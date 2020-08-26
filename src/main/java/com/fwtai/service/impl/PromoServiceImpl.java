package com.fwtai.service.impl;

import com.fwtai.dao.PromoDOMapper;
import com.fwtai.dataobject.PromoDO;
import com.fwtai.service.PromoService;
import com.fwtai.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 营销秒杀活动领域模型服务层,商品对应活动了的价格
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-03 0:45
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@Service
public class PromoServiceImpl implements PromoService{

    @Resource
    private PromoDOMapper promoDOMapper;

    /**
     * 商品对应活动了的价格[根据商品的itemId获取即将进行的或正在进行的秒杀活动] 状态1表示活动还未开始;2表示秒杀活动进行中;3表示活动已结束
     * @param itemId
     * @注意 在录入商品秒杀活动开始时间要小于结束时间的
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019-04-03 00:47:34
    */
    @Override
    public PromoModel getPromoByItemId(final Integer itemId){
        //获取对应商品的秒杀活动信息
        final PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        if(promoDO == null) return null;
        //dataobject--> model 将DO转换为领域模型
        final PromoModel promoModel = this.convertModelFromDataObject(promoDO);
        //判断当前时间是否是秒杀活动即将开始或正在进行秒杀活动
        if(promoModel.getStartDate().isAfterNow()){//开始时间比现在还要后面,表示活动还未开始
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){//结束时间比现在还要前面,表示活动已结束
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    /**
     * dataobject--> model 将DO转换为领域模型
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019-04-03 01:00:07
    */
    private PromoModel convertModelFromDataObject(final PromoDO promoDO){
        if(promoDO == null)return null;
        final PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}