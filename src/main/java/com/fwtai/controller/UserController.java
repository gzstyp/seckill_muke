package com.fwtai.controller;

import com.alibaba.druid.util.StringUtils;
import com.fwtai.controller.viewobject.UserVO;
import com.fwtai.error.BusinessException;
import com.fwtai.error.EmBusinessError;
import com.fwtai.response.BaseController;
import com.fwtai.response.CommonReturnType;
import com.fwtai.service.UserService;
import com.fwtai.service.model.UserModel;
import com.fwtai.tool.ConfigFile;
import com.fwtai.tool.Respond;
import com.fwtai.tool.StatusCode;
import com.fwtai.tool.ToolClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 用户|账号信息[用户和密码是分开存在不同的表]
 */
@RestController
@RequestMapping("/user")
// Spring boot 提供了可以跨越请求的注解,用该注解在跨域的操作session值是无法共享的,怎么办??,要定义域即可解决问题,DEFAULT_ALLOWED_HEADERS可以解决
// DEFAULT_ALLOWED_HEADERS 表示允许跨域传输所有header参数,将用于使用 token 放入header域
// 作为session的共享的跨域请求,它和跨域请求的ajax里的参数 xhrFields : {withCredentials:true} 相对应
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    //它不是单列的,所以不用担心高并发的问题
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/kid/{id}")// http://127.0.0.1/user/kid/1
    @ResponseBody
    public void user(final @PathVariable("id") Integer id,final HttpServletResponse response){
        //通过调用service服务获取对应的用户模型对象并返回给前端
        final UserModel userModel = userService.getUserById(id);
        final UserVO userVO = convertFromModel(userModel);
        ToolClient.responseJson(userVO,response);
    }

    @RequestMapping("/status")// http://127.0.0.1/user/status?value=typ
    @ResponseBody
    public void status(final HttpServletRequest request,final HttpServletResponse response){
        final String value = request.getParameter("value");
        Respond respond = new Respond(StatusCode.Success);
        if(value == null){
            respond = new Respond(StatusCode.InvalidParams);
        }
        if(value != null && value.equals("198")){
            respond = new Respond("非法操作");
        }
        if(value != null && value.equals("201")){
            respond = new Respond(StatusCode.Fail);
        }
        if(value != null && value.equals("202")){
            respond = new Respond(202,"你输入的是" + value);
            final ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
            final HashMap<String,Object> map1 = new HashMap<String,Object>();
            final HashMap<String,Object> map2 = new HashMap<String,Object>();
            final HashMap<String,Object> map3 = new HashMap<String,Object>();
            map1.put("k1",1);
            map1.put("k2","typ");
            map2.put("t","typ");
            map3.put("v",1024);
            list.add(map1);
            list.add(map2);
            list.add(map3);
            respond.setData(list);
        }
        ToolClient.responseJson(respond,response);
    }

    @RequestMapping("/getUser")// http://127.0.0.1/user/getUser?id=1
    @ResponseBody
    public void getUser(final @RequestParam(name = "id") Integer id,final HttpServletResponse response){
        //通过调用service服务获取对应的用户模型对象并返回给前端
        final UserModel userModel = userService.getUserById(id);
        final UserVO userVO = convertFromModel(userModel);
        ToolClient.responseJson(userVO,response);
    }

    @RequestMapping("/get")// http://127.0.0.1/user/get?id=1
    @ResponseBody
    public CommonReturnType get(final @RequestParam(name = "id") Integer id) throws BusinessException{
        //通过调用service服务获取对应的用户模型对象并返回给前端
        final UserModel userModel = userService.getUserById(id);
        //若获取的对应的用户信息不存在
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        final UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    /**
     * 将核心领域模型用户对象转化为可供前端UI使用的viewobject,即model转VO
     * @param
     */
    private UserVO convertFromModel(final UserModel userModel){
        if(userModel == null){
            return null;
        }
        final UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);/*使用这个方法时:必须确保字段名一致且字段类型一致!*/
        return userVO;
    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getOtp", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})// http://127.0.0.1/user/getOtp?telphone=13765128888|getotp.html
    @ResponseBody
    public void getOtp(final @RequestParam(name = "telphone") String telphone,final HttpServletResponse response){
        //需要安装一定的规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(9999);
        randomInt += 10000;//这个值永远都是5个数
        final String otpCode = String.valueOf(randomInt);
        //将OTP验证码同对应的用户的手机号关联[企业级应用一般都用redis保存的,因为redis的key每次赋值都不一样,所以保存的都是最新的],使用HttpSession的方式绑定他的手机号和otpCode
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        System.out.println("telphone="+telphone+"&otpCode="+otpCode);
        ToolClient.responseJson(ToolClient.createJson(ConfigFile.code200,ConfigFile.msg200),response);
    }

    /**
     * 用户注册接口
     * @param
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})// http://127.0.0.1/user/register | register.html
    @ResponseBody
    public void register(final HttpServletResponse response){
        final HashMap<String,String> formParams = getFormParams();
        //验证手机号和对应的otpCode相符合
        final String telphone = formParams.get("telphone");
        //使用 (String) 类型转换不会出现空指针异常,用toString可能会出现异常
        final String inSessionOtpCode = (String) httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(formParams.get("otpCode"),inSessionOtpCode)){
            ToolClient.responseJson(ToolClient.createJsonFail("你输入的手机验证码不正确"),response);
            return;
        }
        //用户的注册流程
        try{
            ToolClient.responseJson(userService.register(formParams),response);
            return;
        }catch(Exception e){
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                ToolClient.responseJson(ToolClient.createJson(ConfigFile.code204,telphone + "<br/>手机号已被注册,请换个手机号试试"),response);
            }else{
                ToolClient.createJsonException(response);
            }
        }
    }

    /**
     * 用户登录接口
     * @param
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})// http://127.0.0.1/user/login | login.html
    @ResponseBody
    public void login(final HttpServletRequest request,final HttpServletResponse response){
        final String json = userService.login(request);
        ToolClient.responseJson(json,response);
    }
}