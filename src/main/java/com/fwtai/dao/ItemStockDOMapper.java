package com.fwtai.dao;

import com.fwtai.dataobject.ItemStockDO;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDOMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    /**
     * 添加商品库存量
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/3 0:04
    */
    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    ItemStockDO selectByItemId(Integer itemId);//获取dataobject对象

    /**
     * 扣减商库存量
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/3 0:03
    */
    int decreaseStock(@Param("itemId") Integer itemId,@Param("amount") Integer amount);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);
}