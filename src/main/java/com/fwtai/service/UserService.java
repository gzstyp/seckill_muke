package com.fwtai.service;

import com.fwtai.service.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 接口
*/
public interface UserService{

    UserModel getUserById(final Integer id);

    String register(final HashMap<String,String> formParams) throws Exception;

    String login(final HttpServletRequest request);
}