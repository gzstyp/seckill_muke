package com.fwtai.error;

import com.fwtai.tool.ConfigFile;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-03-25 1:23
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
public enum EmBusinessError implements CommonError{
    /**
     * 调用的错误类型11001
     */
    PARAMETER_VALIDATION_ERROR(ConfigFile.code202,"参数不合法"),UNKNOWN_ERROR(11002,"未知错误"),ROLE_ERROR(11003,"未知角色"),LOGS_ERROR(11003,"日志出错"),
    /**
     * 1000开头的是用户账号信息相关的错误定义
     */
    USER_NOT_EXIST(10001,"用户不存在"),CHILD_NOT_EXIST(90001,"儿童不存在"),USER_PWD_ERROR(10002,"用户或密码错误");

    private int errCode;

    private String errMsg;

    /**
     * 枚举的构造方法private私有?
     */
    private EmBusinessError(final int errCode,final String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode(){
        return this.errCode;
    }

    @Override
    public String getErrMsg(){
        return this.errMsg;
    }

    /**
     * 提供可传递msg的方法?
     */
    @Override
    public CommonError setErrMsg(final String errMsg){
        this.errMsg = errMsg;
        return this;
    }
}