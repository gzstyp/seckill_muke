package com.fwtai.dao;

import com.fwtai.dataobject.ItemDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemDOMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer id);

    /**
     * 商品销量的增加
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/2 23:59
    */
    int increaseSales(@Param("id") Integer id,@Param("amount")Integer amount);

    List<ItemDO> listItem();

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);
}