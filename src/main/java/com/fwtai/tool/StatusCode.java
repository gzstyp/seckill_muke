package com.fwtai.tool;

/**
 * 通用的请求状态码
 */
public enum StatusCode{
    Error(198,"操作有误"),Success(200,"操作成功"),Fail(199,"操作失败"),InvalidParams(202,"无效的参数");

    private Integer code;

    private String msg;

    StatusCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
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
}