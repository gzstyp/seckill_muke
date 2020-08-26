package com.fwtai.service;

/**
 * 处理订单交易服务接口
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-02 16:31
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public interface OrderService{

    // 通过前端URL传递过来的秒杀活动的promoId，然后在下单接口内校验该promoId是否属于对应商品且活动已开始进行

    // 创建订单
    String createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws Exception;

}