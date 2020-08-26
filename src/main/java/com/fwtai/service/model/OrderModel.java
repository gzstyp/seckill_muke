package com.fwtai.service.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品的订单领域模型-用户下单的交易模型领域
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-02 4:14
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class OrderModel implements Serializable{

    //交易号是明确的属性的,需要自定义字符串,要有一定的意义,201904010417+00001+25565328
    private String id;

    //是谁下单的,下单的userid,用户信息
    private Integer userId;

    //购买商品的编号,商品信息
    private Integer itemId;

    /**在秒杀活动中下单的话,那商品的单价要变为秒杀时购买的单价{若非空则以商品秒杀的价格下单}*/
    private Integer promoId;

    // 需要冗余一个字段,即购买商品的单价,也就是当时购买时的价格,它的变化与OrderModel模型的不会发生变化的，只是记录当时购买的单价
    // 如果 promoId 不为空时则单价是以秒杀的价格
    private BigDecimal itemPrice;

    //购买商品的件数数量
    private Integer amount;

    //购买金额,总价,如果 promoId 不为空时则单价是以秒杀的价格
    private BigDecimal orderPrice;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Integer getItemId(){
        return itemId;
    }

    public void setItemId(Integer itemId){
        this.itemId = itemId;
    }

    public Integer getPromoId(){
        return promoId;
    }

    public void setPromoId(Integer promoId){
        this.promoId = promoId;
    }

    public BigDecimal getItemPrice(){
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice){
        this.itemPrice = itemPrice;
    }

    public Integer getAmount(){
        return amount;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }

    public BigDecimal getOrderPrice(){
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice){
        this.orderPrice = orderPrice;
    }
}