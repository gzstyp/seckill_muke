package com.fwtai.response;

/**
 * 请求响应返回客户端的状态status为 success 或 fail
 */
public final class CommonReturnType{

    /**
     * 表明请求对应的处理结果status为 success 或 fail
     */
    private String status;

    /**
     * 若status=success,则data内返回前端所需的json数据,若status=fail,则data内使用通用的错误码格式
     */
    private Object data;

    public CommonReturnType(){
    }

    public static CommonReturnType create(final Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(final Object result,final String status){
        final CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }
}