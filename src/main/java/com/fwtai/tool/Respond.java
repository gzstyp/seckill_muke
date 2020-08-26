package com.fwtai.tool;

import java.io.Serializable;

/**
 * 通用的请求后响应数据模型
 */
public final class Respond<T> implements Serializable{

    private Integer code;

    private String msg;

    private T data;

    public Respond(){
    }

    /**
     * code为198时就是自定义msg
     */
    public Respond(final String msg){
        this.code = 198;
        this.msg = msg;
    }

    public Respond(final Integer code,final String msg){
        this.code = code;
        this.msg = msg;
    }

    public Respond(final StatusCode statusCode){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public Respond(final Integer code,final String msg,final T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }
}