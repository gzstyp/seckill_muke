package com.fwtai.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端请求及响应工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-03-24 22:19
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class ToolClient{

    /**
     * 若 result > 0 成功,否则失败,即code=200是成功,199是失败
     * @param result
     */
    public static final String createJson(final Integer result){
        final JSONObject json = new JSONObject();
        if(result > 0){
            json.put(ConfigFile.code,ConfigFile.code200);
            json.put(ConfigFile.msg,ConfigFile.msg200);
        }else{
            json.put(ConfigFile.code,ConfigFile.code199);
            json.put(ConfigFile.msg,ConfigFile.msg199);
        }
        return json.toJSONString();
    }

    /**
     * 无效的请求参数或参数不合法
     * @param
     */
    public final static String createJsonInvalidParams(){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,ConfigFile.code202);
        json.put(ConfigFile.msg,ConfigFile.msg202);
        return json.toJSONString();
    }

    /**
     * 生成code为199的json格式数据且msg就是传递的数据
     * @param msg
     */
    public final static String createJsonFail(final String msg){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,ConfigFile.code199);
        json.put(ConfigFile.msg,msg);
        return json.toJSONString();
    }

    /**
     * 根据传递的 result > 0 成功,否则失败,即code=200是成功;code为199是失败,成功的提示对应的msg是successMsg;失败对应的是failMsg
     * @param
     */
    public static final String createJson(final Integer result,final String successMsg,final String failMsg){
        final JSONObject json = new JSONObject();
        if(result > 0){
            json.put(ConfigFile.code,ConfigFile.code200);
            json.put(ConfigFile.msg,successMsg);
        }else{
            json.put(ConfigFile.code,ConfigFile.code199);
            json.put(ConfigFile.msg,failMsg);
        }
        return json.toJSONString();
    }

    /**
     * 自定义json格式数据,规定code=200是成功,其他值都是失败
     * @param code,msg
     */
    public static final String createJson(final Integer code,final String msg){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,code);
        json.put(ConfigFile.msg,msg);
        return json.toJSONString();
    }

    /**
     * 提示系统出现异常
     * @param
     */
    public final static void createJsonException(final HttpServletResponse response){
        responseJson(createJson(ConfigFile.code204,ConfigFile.msg204),response);
    }

    /**
     * 生成简单类型json字符串,仅用于查询返回,客户端只需判断code是否为200才操作,仅用于查询操作,除了list集合之外都可以用data.map获取数据,list的是data.listData
     * @作者 田应平
     * @注意 如果传递的是List则在客户端解析listData的key值,即data.listData;是map或HashMap或PageFormData解析map的key值,即data.map;否则解析obj的key值即data.obj或data.map
     * @用法 解析后判断data.code == AppKey.code.code200 即可
     * @返回值类型 返回的是json字符串,内部采用JSONObject封装,不可用于redis缓存value
     * @创建时间 2017年1月11日 上午10:27:53
     * @QQ号码 444141300
     * @主页 http://www.fwtai.com
     */
    public final static String queryJson(final Object object){
        final JSONObject json = new JSONObject();
        if(ToolString.isBlank(object)){
            return queryEmpty();
        }
        if(object instanceof Map<?,?>){
            final Map<?,?> map = (Map<?,?>) object;
            if(map == null || map.size() <= 0){
                queryEmpty();
            }else {
                json.put(ConfigFile.code,ConfigFile.code200);
                json.put(ConfigFile.msg,ConfigFile.msg200);
                json.put(ConfigFile.map,object);
                return json.toJSONString();
            }
        }
        if(object instanceof List<?>){
            final List<?> list = (List<?>) object;
            if(list == null || list.size() <= 0){
                return queryEmpty();
            }else {
                if (ToolString.isBlank(list.get(0))){
                    return queryEmpty();
                }else {
                    json.put(ConfigFile.code,ConfigFile.code200);
                    json.put(ConfigFile.msg,ConfigFile.msg200);
                    json.put(ConfigFile.listData,object);
                    final String jsonObj = json.toJSONString();
                    final JSONObject j = JSONObject.parseObject(jsonObj);
                    final String listData = j.getString(ConfigFile.listData);
                    if (listData.equals("[{}]")){
                        return queryEmpty();
                    }
                    return jsonObj;
                }
            }
        }
        if(String.valueOf(object).toLowerCase().equals("null") || String.valueOf(object).replaceAll("\\s*", "").length() == 0){
            return queryEmpty();
        }else {
            json.put(ConfigFile.code,ConfigFile.code200);
            json.put(ConfigFile.msg,ConfigFile.msg200);
            json.put(ConfigFile.obj,object);
            final String jsonObj = json.toJSONString();
            final JSONObject j = JSONObject.parseObject(jsonObj);
            final String obj = j.getString(ConfigFile.obj);
            if (obj.equals("{}")){
                return queryEmpty();
            }
            return jsonObj;
        }
    }

    private static final String queryEmpty(){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,ConfigFile.code201);
        json.put(ConfigFile.msg,ConfigFile.msg201);
        return json.toJSONString();
    }

    /**
     * 回写给客户端,即响应客户端请求,是json格式
     * @param
     */
    public final static void responseJson(final Object jsonObject,final HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        PrintWriter writer = null;
        try{
            writer = response.getWriter();
            if(jsonObject == null){
                writer.write(createJson(ConfigFile.code201,ConfigFile.msg201));
                writer.flush();
                writer.close();
                return;
            }
            if(jsonObject instanceof String){
                writer.write(JSON.parse(jsonObject.toString()).toString());
                writer.flush();
                writer.close();
                return;
            }else{
                writer.write(JSONArray.toJSONString(jsonObject));
                writer.flush();
                writer.close();
                return;
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(null != writer){
                writer.close();
            }
        }
    }

    /**
     * 获取表单参数,不含文件域
     * @param request
     */
    public final static HashMap<String,String> getFormParams(final HttpServletRequest request){
        final HashMap<String,String> params = new HashMap<String,String>();
        final Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()){
            final String key = paramNames.nextElement();
            final String[] values = request.getParameterValues(key);
            String value = "";
            if(values == null){
                value = "";
            }else{
                for(int i = 0; i < values.length; i++){
                    value = values[i] + ",";
                }
                value = value.substring(0,value.length() - 1);
            }
            params.put(key,value);
        }
        return params;
    }

    /**
     * 把form表单数据转为对象[Map<String,String> | HashMap<String,Object> | bean]
     * @param request
     * @param bean
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 02:42:04
    */
    public final static void formConvertBean(final HttpServletRequest request,final Object bean){
        try {
            /*包shiro-spring*/
            org.apache.commons.beanutils.BeanUtils.populate(bean,request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证必要的字段是否为空,不验证ckey密钥,一般在service层调用,如果返回为 null 则验证成功,否则失败;适用于增、删、改、查操作!
     */
    public final static String validateFields(final PageFormData pageFormData,final String[] fields){
        final JSONObject json = new JSONObject();
        if(ToolString.isBlank(pageFormData) || ToolString.isBlank(fields)){
            json.put(ConfigFile.code,ConfigFile.code202);
            json.put(ConfigFile.msg,ConfigFile.msg202);
            return json.toJSONString();
        }
        if(!ToolString.isBlank(fields)){
            boolean flag = false;
            for(String p : fields){
                if(ToolString.isBlank(pageFormData.get(p))){
                    flag = true;
                    break;
                }
            }
            if(flag){
                json.put(ConfigFile.code,ConfigFile.code202);
                json.put(ConfigFile.msg,ConfigFile.msg202);
                return json.toJSONString();
            }
        }
        return null;
    }

    /**
     * 验证必要的字段是否为空,不验证ckey密钥,一般在service层调用,如果返回为 null 则验证成功,否则失败;适用于增、删、改、查操作!
     */
    public final static String validateForm(final HashMap<String,String> formData,final String[] fields){
        final JSONObject json = new JSONObject();
        if(ToolString.isBlank(formData) || ToolString.isBlank(fields)){
            json.put(ConfigFile.code,ConfigFile.code202);
            json.put(ConfigFile.msg,ConfigFile.msg202);
            return json.toJSONString();
        }
        if(!ToolString.isBlank(fields)){
            boolean flag = false;
            for(String p : fields){
                if(ToolString.isBlank(formData.get(p))){
                    flag = true;
                    break;
                }
            }
            if(flag){
                json.put(ConfigFile.code,ConfigFile.code202);
                json.put(ConfigFile.msg,ConfigFile.msg202);
                return json.toJSONString();
            }
        }
        return null;
    }
}