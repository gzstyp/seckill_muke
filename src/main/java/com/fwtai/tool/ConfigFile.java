package com.fwtai.tool;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-03-26 16:18
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
public enum ConfigFile{
    ;

    /**
     * 统一全局的code的状态码json关键字key关键字响应给客户端
     */
    public final static String code = "code";

    /**
     * 统一全局的msg提示消息json关键字key响应给客户端
     */
    public final static String msg = "msg";

    /**
     * 自定义code及msg
     */
    public final static int code198 = 198;

    /**
     * 操作失败
     */
    public final static int code199 = 199;

    /**
     * 操作失败
     */
    public final static String msg199 = "操作失败";

    /**
     * 操作成功
     */
    public final static int code200 = 200;

    /**
     * 操作成功
     */
    public final static String msg200 = "操作成功";

    /**
     * 暂无数据
     */
    public final static int code201 = 201;

    /**
     * 暂无数据
     */
    public final static String msg201 = "暂无数据";

    /**
     * 请求参数不完整
     */
    public final static int code202 = 202;

    /**
     * 请求参数不完整
     */
    public final static String msg202 = "参数不合法";

    /**
     * 密钥验证失败
     */
    public final static int code203 = 203;

    /**
     * 密钥验证失败
     */
    public final static String msg203 = "密钥验证失败";

    /**
     * 系统出现异常
     */
    public final static int code204 = 204;

    /**
     * 系统出现异常
     */
    public final static String msg204 = "系统出现错误";

    /**
     * 未登录或登录超时
     */
    public final static int code205 = 205;

    /**
     * 未登录或登录超时
     */
    public final static String msg205 = "未登录或登录超时!";

    /**
     * 账号或密码不正确
     */
    public final static int code206 = 206;

    /**
     * 账号或密码不正确
     */
    public final static String msg206 = "账号或密码不正确";

    /**统一全局的total总条数|总记录数json关键字key响应给客户端*/
    public final static String total = "total";
    /**统一全局的totalPage总页数json关键字key响应给客户端*/
    public final static String totalPage = "totalPage";
    /**统一全局的Easyui里的datagrid数据返回json关键字key响应给客户端*/
    public final static String rows = "rows";
    /**统一全局的map数据json关键字key响应给客户端*/
    public final static String map = "map";
    /**统一全局的obj数据json关键字key响应给客户端*/
    public final static String obj = "obj";
    /**统一全局的callback调用接口方法json关键字key响应给客户端*/
    public final static String callback = "callback";
    /**统一全局的pageSize每页大小json关键字key响应给客户端或作为Mybatis的分页参数*/
    public final static String pageSize = "pageSize";
    /**统一全局的current当前页json关键字key响应给客户端*/
    public final static String current = "current";
    /**统一全局的listData数据集合(含分页的数据)json关键字key响应给客户端*/
    public final static String listData = "listData";
}