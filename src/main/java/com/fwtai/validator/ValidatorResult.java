package com.fwtai.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * 表单必要验证插件,约定大于执行的操作
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-01 14:04
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class ValidatorResult{

    /**
     * 校验结果是否有错
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 14:07
    */
    private boolean hasErrors = false;//初始化防止空指针异常

    /**
     * 存放错误信息的map集合
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 14:08
    */
    private HashMap<String,String> errorMsgMap = new HashMap<String,String>();//初始化防止空指针异常

    /**
     * 实现通用的格式化字符串信息获取错误的结果msg方法
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 14:09:32
    */
    public String getMsg(){
        return StringUtils.join(errorMsgMap.values(),"<br/>");
    }

    public boolean isHasErrors(){
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors){
        this.hasErrors = hasErrors;
    }

    public HashMap<String,String> getErrorMsgMap(){
        return errorMsgMap;
    }

    public void setErrorMsgMap(HashMap<String,String> errorMsgMap){
        this.errorMsgMap = errorMsgMap;
    }
}