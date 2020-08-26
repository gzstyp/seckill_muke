package com.fwtai.controller;

import com.fwtai.response.BaseController;
import com.fwtai.service.OrderService;
import com.fwtai.service.model.UserModel;
import com.fwtai.tool.ConfigFile;
import com.fwtai.tool.ToolClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-02 18:55
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@RestController
@RequestMapping("/order")
// Spring boot 提供了可以跨越请求的注解,用该注解在跨域的操作session值是无法共享的,怎么办??,要定义域即可解决问题,DEFAULT_ALLOWED_HEADERS可以解决
// DEFAULT_ALLOWED_HEADERS 表示允许跨域传输所有header参数,将用于使用 token 放入header域
// 作为session的共享的跨域请求,它和跨域请求的ajax里的参数 xhrFields : {withCredentials:true} 相对应
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OrderService orderService;

    /**
     * 订单服务,用户交易行为处理
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/2 18:57
    */
    @RequestMapping(value = "/createOrder",method = RequestMethod.POST)// http://127.0.0.1/item/createitem.html
    @ResponseBody
    public void create(@RequestParam(name = "itemId") Integer itemId,@RequestParam (name = "amount") Integer amount,@RequestParam (name = "promoId",required = false) Integer promoId,final HttpServletResponse response){
        try {
            //获取用户登录信息
            final Boolean is_login = (Boolean) this.request.getSession().getAttribute("IS_LOGIN");
            if(is_login == null || !is_login){
                ToolClient.responseJson(ToolClient.createJson(ConfigFile.code205,"未登录或已超时,还不能下单请登录!"),response);
                return;
            }
            final UserModel userModel = (UserModel) this.request.getSession().getAttribute("LOGIN_USER");
            if(userModel == null){
                ToolClient.responseJson(ToolClient.createJson(ConfigFile.code205,"未登录或登录已超时,请登录!"),response);
                return;
            }
            final String json = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
            ToolClient.responseJson(json,response);
        } catch (Exception e) {
            e.printStackTrace();
            ToolClient.createJsonException(response);
        }
    }
}