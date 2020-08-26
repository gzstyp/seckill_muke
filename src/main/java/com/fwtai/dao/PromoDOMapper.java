package com.fwtai.dao;

import com.fwtai.dataobject.PromoDO;

/**
 * 营销秒杀活动领域
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-03 00:35:35
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public interface PromoDOMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    PromoDO selectByPrimaryKey(Integer id);

    /**获取对应商品的秒杀活动信息*/
    PromoDO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
}