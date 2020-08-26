package com.fwtai.dataobject;

/**
 * 商品的订单领域模型-用户下单的交易模型领域
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019/4/2 16:31
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public class OrderDO {

    private String id;

    /**用户的id*/
    private Integer userId;

    /**商品的id*/
    private Integer itemId;

    /**商品的单价价格*/
    private Double itemPrice;

    /**购买数量*/
    private Integer amount;

    /**订单的总金额*/
    private Double orderPrice;

    /**商品秒杀的id*/
    private Integer promoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPromoId(){
        return promoId;
    }

    public void setPromoId(Integer promoId){
        this.promoId = promoId;
    }
}