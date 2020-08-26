package com.fwtai.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fwtai.dao.UserDOMapper;
import com.fwtai.dao.UserPasswordDOMapper;
import com.fwtai.dataobject.UserDO;
import com.fwtai.dataobject.UserPasswordDO;
import com.fwtai.service.UserService;
import com.fwtai.service.model.UserModel;
import com.fwtai.tool.ConfigFile;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolSHA;
import com.fwtai.tool.ToolString;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-03-24 19:23
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDOMapper userDOMapper;

    @Resource
    private UserPasswordDOMapper userPasswordDOMapper;

    //为什么本方法没有返回值,在企业应用中,因为这个UserDO绝对不能透传给前台,DO只是与数据库的orpper映射，它与数据库一一对应!所以在services层必须有一个叫model的感念
    @Override
    public UserModel getUserById(final Integer id){
        //通过userDOMapper获取到对应的用户dataobject,为什么本方法没有返回值
        final UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null){
            return null;
        }
        //通过用户id获取用户加密密码的信息
        final UserPasswordDO userPasswordDO = userPasswordDOMapper.selectPasswordByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public String register(final HashMap<String,String> formParams) throws Exception{
        if(formParams == null || formParams.size() <= 0){
            return ToolClient.createJsonInvalidParams();
        }
        final String validate = ToolClient.validateForm(formParams,new String[]{"name","gender","age","telphone","password"});
        if(!ToolString.isBlank(validate))return validate;
        final String password = formParams.get("password");
        final String telphone = formParams.get("telphone");
        final UserModel userModel = new UserModel();
        userModel.setEncrptPassword(ToolSHA.encoder(password,telphone));
        userModel.setAge(Integer.valueOf(formParams.get("age")));
        userModel.setName(formParams.get("name"));
        userModel.setGender(new Byte(formParams.get("gender")));
        userModel.setRegisterMode("bytelpone");
        userModel.setTelphone(formParams.get("telphone"));
        //实现model转为dataobject方法
        final UserDO userDO = convertFromModel(userModel);
        final int rows = userDOMapper.insertSelective(userDO);//排查链路的问题,id是自增的字段,需要设置 useGeneratedKeys="true" keyProperty="id"
        userModel.setId(userDO.getId());//从自增的字段获取返回值，并赋值,id是自增的字段
        final UserPasswordDO userPasswordDO = convertPassWordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return ToolClient.createJson(rows,"恭喜,注册成功!","抱歉,注册失败!");
    }

    /**
     * 用户登录服务
     * @param request
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 05:53:05
    */
    @Override
    public String login(final HttpServletRequest request){
        final HashMap<String,String> formParams = ToolClient.getFormParams(request);
        //入参校验
        final String validate = ToolClient.validateForm(formParams,new String[]{"telphone","password"});
        if(!ToolString.isBlank(validate))return validate;
        //通过用户登录的手机号获取用户的信息
        final String telphone = formParams.get("telphone");
        final String pwd = formParams.get("password");
        final String json = ToolClient.createJson(ConfigFile.code206,ConfigFile.msg206);
        final UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO == null){
            return json;
        }
        final UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByPrimaryKey(userDO.getId());
        if(userPasswordDO == null){
            return json;
        }
        //比对用户信息内的密码和用户传递过来的密码是否一致
        final String password = userPasswordDO.getEncrptPassword();
        final String encoder = ToolSHA.encoder(pwd,telphone);
        if(!StringUtils.equals(password,encoder)){
            return json;
        }else{
            //将用户登录凭证加入到Session中
            final UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
            request.getSession().setAttribute("IS_LOGIN",true);
            request.getSession().setAttribute("LOGIN_USER",userModel);
            return ToolClient.createJson(ConfigFile.code200,"登录成功!");
        }
    }

    /**
     * 实现dataobject转为model方法
     * @param
     */
    private UserModel convertFromDataObject(final UserDO userDO,final UserPasswordDO userPasswordDO){
        if(userDO == null){
            return null;
        }
        final UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);/*使用这个方法时:必须确保字段名一致且字段类型一致!*/
        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }

    /**
     * 实现model转为dataobject方法,即Model转DO
     * @param
     */
    private UserDO convertFromModel(final UserModel userModel){
        //验证是否为空
        if(userModel == null){
            return null;
        }
        final UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);/*使用这个方法时:必须确保字段名一致且字段类型一致!*/
        return userDO;
    }

    /**
     * 处理密码
     * @param
     */
    private UserPasswordDO convertPassWordFromModel(final UserModel userModel){
        //验证是否为空
        if(userModel == null){
            return null;
        }
        final UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }
}