package com.fwtai.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品领域模型
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-01 16:17
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
public final class ItemModel implements Serializable{

    //id主键
    private Integer id;

    //商品名
    @NotBlank(message = "商品名称不能为空")
    private String title;

    //商品价格
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price;//不能用duble类型，否则在前端显示有精度问题!!!

    //商品的库存,它与商品是一一对应的
    @NotNull(message = "商品库存不能为空")
    private Integer stock;

    //商品的描述信息
    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    //商品的销量,当用户发生交易行为后，异步修改销量+1,从而不影响下单主链路
    private Integer sales;

    //商品图片url
    @NotBlank(message = "商品图片不能为空")
    private String imgUrl;

    /**使用聚合模型,即java类的嵌套,将PromoModel冗余进来,如果promoModel不为空则表示该商品参与秒杀活动的商品
     * [表示该商品用拥有还未结束的秒杀活动],包含了还未开始以及正在进行的秒杀活动
    */
    private PromoModel promoModel;

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

    /**使用聚合模型,即java类的嵌套,将PromoModel冗余进来,如果promoModel不为空则表示该商品参与秒杀活动的商品
     * [表示该商品用拥有还未结束的秒杀活动],包含了还未开始以及正在进行的秒杀活动
    */
    public PromoModel getPromoModel(){
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel){
        this.promoModel = promoModel;
    }
}