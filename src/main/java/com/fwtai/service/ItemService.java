package com.fwtai.service;

import com.fwtai.service.model.ItemModel;

import java.util.List;

/**
 * 商品领域
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-01 16:36
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public interface ItemService{

    /**
     * 创建商品,有Model返回值
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 16:39:07
    */
    ItemModel createItem(final ItemModel itemModel) throws Exception;

    /**
     * 创建商品,有json格式返回值
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 16:39:07
     */
    String create(final ItemModel itemModel) throws Exception;
    
    /**
     * 商量列表浏览
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 16:39:56
    */
    List<ItemModel> listItem();

    /**
     * 商品详情浏览-->通过查询商品是否存在秒杀活动内来确定是否存在商品秒杀活动
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月1日 16:40:00
    */
    ItemModel getItem(final Integer id);

    /**
     * 扣减库存
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月2日 16:54:10
    */
    boolean decreaseStock(Integer itemId,Integer amount)throws Exception;

    /**
     * 商品销量的增加-->库存扣减成功且下单成功
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月2日 23:18:47
    */
    Integer increaseSales(Integer itemId,Integer amount)throws Exception;
}