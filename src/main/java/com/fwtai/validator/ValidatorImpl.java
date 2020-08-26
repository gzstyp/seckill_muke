package com.fwtai.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-01 14:16
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@Component
public class ValidatorImpl implements InitializingBean{

    private Validator validator;

    /**
     * 当spring初始化完成后,会回调该方法
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 14:18
    */
    @Override
    public void afterPropertiesSet() throws Exception{
        //将hibernate的Validator通过工厂的初始化十七实例化　
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 实现校验方法并返回校验结果,支持自定义验证规则
     * @用法
     * HashMap<String ,StringBuffer> hashMap = validator.validate(userModel);
     *         if(hashMap != null && hashMap.size() > 0){
     *             final StringBuffer sb = new StringBuffer();
     *             for(final String key : hashMap.keySet()){
     *                 sb.append(hashMap.get(key) + "<br/>");
     *             }
     *             ToolClient.responseJson(ToolClient.createJson(ConfigFile.code202,sb.toString()),response);
     *             return;
     *         }
     * @param　obj
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 14:44
    */
    public <T> HashMap<String,StringBuffer> validate(final T obj){
        HashMap<String,StringBuffer> errorMap = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj,Default.class);
        if(set != null && set.size() >0 ){
            errorMap = new HashMap<String,StringBuffer>();
            String property = null;
            for(ConstraintViolation<T> cv : set){
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                if(errorMap.get(property) != null){
                    errorMap.get(property).append("," + cv.getMessage());
                }else{
                    StringBuffer sb = new StringBuffer();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }
        return errorMap;
    }
    
    /**
     * 实现校验方法并返回校验结果
     * @用法
     * ValidatorResult vrt = this.validator.validator(userModel);
     * if(vrt.isHasErrors()){
     *      ToolClient.responseJson(ToolClient.createJson(ConfigFile.code202,vrt.getMsg()),response);
     *      return;
     * }
     * @param bean
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 14:36:58
    */
    public ValidatorResult validator(final Object bean){
        final ValidatorResult validatorResult = new ValidatorResult();
        final Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        //如果有错误,就遍历错误信息
        if(constraintViolationSet != null && constraintViolationSet.size() > 0){
            validatorResult.setHasErrors(true);
            Iterator<ConstraintViolation<Object>> iterator = constraintViolationSet.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<Object> obj = iterator.next();
                final String k = obj.getPropertyPath().toString();
                final String v = obj.getMessage();
                validatorResult.getErrorMsgMap().put(k,v);
            }
        }
        return validatorResult;
    }
}