package com.fwtai.controller.example;

import com.fwtai.error.BusinessException;
import com.fwtai.error.EmBusinessError;
import com.fwtai.response.BaseController;
import com.fwtai.service.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-03-25 3:10
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
@Controller
@RequestMapping("/child")
public class ChildController extends BaseController{

    @RequestMapping("/get")// http://127.0.0.1/child/get?id=101
    @ResponseBody
    public void get(final @RequestParam(name = "id") Integer id) throws BusinessException{
        //通过调用service服务获取对应的用户模型对象并返回给前端
        final UserModel userModel = null;
        //若获取的对应的用户信息不存在
        if(userModel == null){
            throw new BusinessException(EmBusinessError.CHILD_NOT_EXIST);
        }
    }

    @RequestMapping("/zero")// http://127.0.0.1/child/zero?id=101
    @ResponseBody
    public void zero(final @RequestParam(name = "id") Integer id) throws BusinessException{
        int i = 1 / 0;
    }
}