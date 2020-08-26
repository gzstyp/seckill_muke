package com.fwtai.service;

import com.fwtai.service.model.PromoModel;

/**
 * 营销秒杀活动领域模型接口层,商品对应活动了的价格!
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-03 0:40
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public interface PromoService{

    /**
     * 商品对应活动了的价格[根据商品的itemId获取即将进行的或正在进行的秒杀活动]
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019-04-03 00:47:04
    */
    PromoModel getPromoByItemId(final Integer itemId);

}