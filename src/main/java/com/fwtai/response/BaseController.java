package com.fwtai.response;

import com.fwtai.error.BusinessException;
import com.fwtai.error.EmBusinessError;
import com.fwtai.tool.ConfigFile;
import com.fwtai.tool.PageFormData;
import com.fwtai.tool.ToolClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 生成PageFormData对象,方便获取表单的数据及处理表单
 */
public class BaseController{

    public final static String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 获取PageFormData
     */
    public final PageFormData getPageFormData(){
        return new PageFormData(getRequest());
    }

    /**
     * 获取request对象
     */
    public final HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取rResponse对象
     */
    public final HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取表单参数
     */
    public final HashMap<String,String> getFormParams(){
        return ToolClient.getFormParams(getRequest());
    }

    /**
     * 获取session
     */
    public final HttpSession getSession(){
        return getRequest().getSession(false);
    }

    /**
     * 通用模型,所以要改成面向对象编程
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public final void handlerException(final Exception exception,final HttpServletResponse response){
        final HashMap<String,Object> map = new HashMap<>();
        if(exception instanceof BusinessException){
            final BusinessException businessException = (BusinessException) exception;
            map.put("code",ConfigFile.code198);
            map.put("msg",businessException.getErrMsg());
        }else{
            map.put("code",ConfigFile.code198);
            map.put("msg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        exception.printStackTrace();
        ToolClient.responseJson(map,response);
    }
}