package com.fwtai.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用的字符串工具类
 */
public final class ToolString{

    /**
     * 验证是否为空,为空时返回true,否则返回false,含obj是否为 _单独的下划线特殊字符,是则返回true,否则返回false
     */
    public final static boolean isBlank(final Object obj){
        if(obj == null)
            return true;
        final String temp = String.valueOf(obj);
        if(temp.toLowerCase().equals("null"))
            return true;
        final String key = obj.toString().replaceAll("\\s*","");
        if(key.equals("") || key.length() <= 0)
            return true;
        if(key.length() == 1 && key.equals("_"))
            return true;
        if(obj instanceof List<?>){
            final List<?> list = (List<?>) obj;
            return list == null || list.size() <= 0;
        }
        if(obj instanceof Map<?,?>){
            final Map<?,?> map = (Map<?,?>) obj;
            return map == null || map.size() <= 0;
        }
        if(obj instanceof String[]){
            boolean flag = false;
            final String[] require = (String[]) obj;
            for(int i = 0; i < require.length; i++){
                if(require[i] == null || require[i].length() == 0 || require[i].replaceAll("\\s*","").length() == 0){
                    flag = true;
                    break;
                }
            }
            return flag;
        }
        if(obj instanceof JSONObject){
            final JSONObject json = (JSONObject) obj;
            return json.isEmpty();
        }
        return false;
    }

    /**
     * <strong style='color:#f69;'>解析json对象字符串,HashMap里的key全是小写</strong>
     * @提示 <strong style='color:#369;'>json对象就是以{"开头,即HashMap<String,String>;json数组就是以[{"开头,即ArrayList<HashMap<String,String>>;json全都是String</strong>
     * @作者 田应平
     * @创建时间 2017年3月7日 20:42:05
     * @QQ号码 444141300
     * @主页 http://www.fwtai.com
    */
    public final static HashMap<String,String> parseJsonObject(final Object json){
        final HashMap<String, String> jsonMap = new HashMap<String, String>();
        if(isBlank(json)) return jsonMap;
        try {
            final JSONObject jsonObject = JSONObject.parseObject(json.toString());
            for (String key : jsonObject.keySet()){
                jsonMap.put(key.toLowerCase(),(jsonObject.get(key) == null || jsonObject.get(key).toString().length() <= 0) ? "" : String.valueOf(jsonObject.get(key)));
            }
            return jsonMap;
        } catch (Exception e){
            return jsonMap;
        }
    }

    /**
     * <strong style='color:#f69;'>解析json数组字符串,ArrayList里的HashMap的key全是小写</strong>
     * @提示 <strong style='color:#369;'>json对象就是以{"开头,即HashMap<String,String>;json数组就是以[{"开头,即ArrayList<HashMap<String,String>>;json全都是String</strong>
     * @作者 田应平
     * @创建时间 2017年3月7日 20:42:12
     * @QQ号码 444141300
     * @主页 http://www.fwtai.com
    */
    public final static ArrayList<HashMap<String,String>> parseJsonArray(final Object jsonArray){
        final ArrayList<HashMap<String, String>> listResult = new ArrayList<HashMap<String, String>>();
        if(isBlank(jsonArray)) return listResult;
        try {
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();//初始化,以免出现空指针异常
            list = JSON.parseObject(jsonArray.toString(), new TypeReference<ArrayList<HashMap<String, String>>>(){});
            if(list != null && list.size() > 0){
                for (int i = 0; i < list.size(); i++){
                    final HashMap<String, String> hashMap = new HashMap<String, String>();
                    for (String key : list.get(i).keySet()){
                        hashMap.put(key.toLowerCase(),(list.get(i).get(key) == null || list.get(i).get(key).toString().length() <= 0) ? "" : list.get(i).get(key).toString());
                    }
                    listResult.add(hashMap);
                }
                return listResult;
            } else {
                return listResult;
            }
        } catch (Exception e){
            return listResult;
        }
    }
}