package com.fwtai.controller.viewobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 前端需要显示的商品数据
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-01 23:34
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
public class ItemVO implements Serializable{

    //id主键
    private Integer id;

    //商品名称
    private String title;

    //商品价格
    private BigDecimal price;//不能用duble类型，否则在前端显示有精度问题!!!

    //商品的库存,它与商品是一一对应的
    private Integer stock;

    //商品的描述信息
    private String description;

    //商品的销量,当用户发生交易行为后，异步修改销量+1,从而不影响下单主链路
    private Integer sales;

    //商品图片url
    private String imgUrl;

    /**记录商品是否在秒杀活动中,及本商品对应的状态: 0表示该商品没有秒杀活动;1表示秒杀活动待开始;2表示商品秒杀活动正在进行中(正在进行秒杀活动则存在秒杀活动的价格吧!!)*/
    private Integer promoStatus;

    /**若正在进行秒杀活动则存在秒杀活动的价格*/
    private BigDecimal promoPrice;

    /**商品秒杀活动表的id主键,在用户交易行为需要用到对应的秒杀活动id*/
    private Integer promoId;

    /**秒杀活动的开始时间,用来倒计时显示用*/
    private String startDate;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public Integer getStock(){
        return stock;
    }

    public void setStock(Integer stock){
        this.stock = stock;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Integer getSales(){
        return sales;
    }

    public void setSales(Integer sales){
        this.sales = sales;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public Integer getPromoStatus(){
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus){
        this.promoStatus = promoStatus;
    }

    public BigDecimal getPromoPrice(){
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice){
        this.promoPrice = promoPrice;
    }

    public Integer getPromoId(){
        return promoId;
    }

    public void setPromoId(Integer promoId){
        this.promoId = promoId;
    }

    public String getStartDate(){
        return startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
}