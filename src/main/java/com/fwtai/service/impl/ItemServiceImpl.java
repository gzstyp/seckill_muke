package com.fwtai.service.impl;

import com.fwtai.dao.ItemDOMapper;
import com.fwtai.dao.ItemStockDOMapper;
import com.fwtai.dataobject.ItemDO;
import com.fwtai.dataobject.ItemStockDO;
import com.fwtai.error.BusinessException;
import com.fwtai.error.EmBusinessError;
import com.fwtai.service.ItemService;
import com.fwtai.service.PromoService;
import com.fwtai.service.model.ItemModel;
import com.fwtai.service.model.PromoModel;
import com.fwtai.tool.ToolClient;
import com.fwtai.validator.ValidatorImpl;
import com.fwtai.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品领域接口服务实现
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-01 18:51
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@Service
public class ItemServiceImpl implements ItemService{

    @Resource
    private ValidatorImpl validator;

    @Resource
    private ItemDOMapper itemDOMapper;

    @Resource
    private ItemStockDOMapper itemStockDOMapper;

    @Resource
    private PromoService promoService;//获取活动商品的信息,即查询该商品来确定是否存在商品秒杀活动内

    //Model转DO
    private ItemDO convertItemDOFromItemModel(final ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        final ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice());//可能会有问题
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        final ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());//前端传递来的
        return itemStockDO;
    }

    //创建商品,有Model返回值
    @Override
    @Transactional
    public ItemModel createItem(final ItemModel itemModel) throws Exception{
        //1、入库前的入参校验
        ValidatorResult validatorResult = this.validator.validator(itemModel);
        if(validatorResult.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validatorResult.getMsg());
        }
        //2、转换itemModel-->dataObject
        final ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);
        //3、写入数据库
        final Integer rows = itemDOMapper.insertSelective(itemDO);//执行本方法会返回该DO对象
        final ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDO.setItemId(itemDO.getId());
        itemStockDOMapper.insertSelective(itemStockDO);
        //4、返回创建完成的对象,因为要让上游知道创建成功后的对象是什么样的状态
        return this.getItem(itemDO.getId());
    }

    //创建商品,有json格式返回值
    @Override
    @Transactional
    public String create(final ItemModel itemModel) throws Exception{
        //1、入库前的入参校验
        final ValidatorResult validatorResult = this.validator.validator(itemModel);
        if(validatorResult.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validatorResult.getMsg());
        }
        //2、转换itemModel-->dataObject
        final ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);
        //3、写入数据库
        final Integer rows = itemDOMapper.insertSelective(itemDO);//执行本方法会返回该DO对象
        final ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDO.setItemId(itemDO.getId());
        itemStockDOMapper.insertSelective(itemStockDO);
        return ToolClient.createJson(rows,"创建商品成功!","创建商品失败");
    }

    //商量列表浏览
    @Override
    public List<ItemModel> listItem(){
        final List<ItemDO> listItem = itemDOMapper.listItem();
        final List<ItemModel> itemModels = new ArrayList<>();
        for(int i = 0; i < listItem.size(); i++){
            final ItemDO itemDO = listItem.get(i);
            final ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            final ItemModel itemModel = this.convertModelFromData(itemDO,itemStockDO);
            itemModels.add(itemModel);
        }
        return itemModels;
    }

    //商品详情浏览-->通过查询商品是否存在秒杀活动内来确定是否存在商品秒杀活动
    @Override
    public ItemModel getItem(Integer id){
        final ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null){
            return null;
        }else{
            //获取操作库存数量
            final ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());//获取dataobject对象
            final ItemModel itemModel = this.convertModelFromData(itemDO,itemStockDO);
            /**获取活动商品的信息,即查询该商品来确定是否存在商品秒杀活动内*/
            final PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
            if(promoModel != null && promoModel.getStatus().intValue() != 3){
                itemModel.setPromoModel(promoModel);/**采用模型聚合的方式将秒杀商品和秒杀的活动关联在一起!*/
            }
            //itemModel.getPromoModel();
            return itemModel;
        }
    }

    //扣减库存为true更新库存成功,false为失败;涉及到扣减库存,所以要事务一致性!
    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId,Integer amount) throws Exception{
        final int rows = itemStockDOMapper.decreaseStock(itemId,amount);
        return rows > 0 ? true : false;
    }

    /**
     * 商品销量的增加-->库存扣减成功且下单成功,当然这个也必须在事务内!
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019年4月2日 23:20:28
    */
    @Override
    @Transactional
    public Integer increaseSales(Integer itemId,Integer amount) throws Exception{
        return itemDOMapper.increaseSales(itemId,amount);
    }

    //将dataobject-->model
    private ItemModel convertModelFromData(final ItemDO itemDO,final ItemStockDO itemStockDO){
        if(itemDO == null || itemStockDO == null){
            return null;
        }
        final ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(itemDO.getPrice());
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}