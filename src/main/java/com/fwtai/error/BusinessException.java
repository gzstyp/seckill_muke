package com.fwtai.error;

/**
 * 业务异常处理,采用包装器业务异常实现
 */
public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;

    /**
     * 直接接收EmBusinessError的传参,用于构造业务异常
     */
    public BusinessException(final CommonError commonError){
        super();//这个必须要有!
        this.commonError = commonError;
    }

    /**
     * 接收自定义的errMsg的方法构造业务异常
     */
    public BusinessException(final CommonError commonError,final String errMsg){
        super();//这个必须要有!
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode(){
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg(){
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg){
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}