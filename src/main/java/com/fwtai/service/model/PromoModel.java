package com.fwtai.service.model;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 营销秒杀活动领域模型
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-03 0:15
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class PromoModel implements Serializable{

    private Integer id;

    /**秒杀活动状态1表示活动还未开始;2表示秒杀活动进行中;3表示活动已结束*/
    private Integer status;//该字段跟数据库没有半毛钱关系,仅仅是一个状态

    /**秒杀活动名称*/
    private String promoName;

    /**秒杀活动开始时间*/
    private DateTime startDate;

    /**杀活动结束时间*/
    private DateTime endDate;

    /**秒杀活动适用的商品*/
    private Integer itemId;

    /**秒杀活动的商品价格*/
    private BigDecimal promoItemPrice;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public String getPromoName(){
        return promoName;
    }

    public void setPromoName(String promoName){
        this.promoName = promoName;
    }

    public DateTime getStartDate(){
        return startDate;
    }

    public void setStartDate(DateTime startDate){
        this.startDate = startDate;
    }

    public DateTime getEndDate(){
        return endDate;
    }

    public void setEndDate(DateTime endDate){
        this.endDate = endDate;
    }

    public Integer getItemId(){
        return itemId;
    }

    public void setItemId(Integer itemId){
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice(){
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice){
        this.promoItemPrice = promoItemPrice;
    }
}