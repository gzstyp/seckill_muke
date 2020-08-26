package com.fwtai.tool;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 密码生成密码加密数据
*/
public final class ToolSHA{

    private static final String M = "SHA-1";

    private final static String defaultKey = "www.fwtai.com";

    /**
     * SHA-1方式密码加密,仅加密一次
    */
    private final static String encryptHash(final String object){
        return String.valueOf(new Sha256Hash(object,defaultKey));
    }

    /**
     * @param password 密码
     * @param userName 盐值
     * SHA-1方式用户名密码加密,不可逆向
    */
    public final static String encoder(final String password,final String userName){
        return String.valueOf(new SimpleHash(M,password,encryptHash(userName)));
    }
}