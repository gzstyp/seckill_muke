package com.fwtai.service.impl;

import com.fwtai.dao.OrderDOMapper;
import com.fwtai.dao.SequenceDOMapper;
import com.fwtai.dataobject.OrderDO;
import com.fwtai.dataobject.SequenceDO;
import com.fwtai.service.ItemService;
import com.fwtai.service.OrderService;
import com.fwtai.service.UserService;
import com.fwtai.service.model.ItemModel;
import com.fwtai.service.model.OrderModel;
import com.fwtai.service.model.UserModel;
import com.fwtai.tool.ConfigFile;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolString;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 处理订单交易服务接口
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-02 16:35
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@Service
public class OrderServiceImpl implements OrderService{

    @Resource
    private ItemService itemService;

    @Resource
    private UserService userService;

    @Resource
    private OrderDOMapper orderDOMapper;

    @Resource
    private SequenceDOMapper sequenceDOMapper;

    //创建订单
    @Override
    @Transactional
    public String createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount)throws Exception{
        //1、校验下单状态,用户是否合法，是否是黑名单之类的;下单的商品是否存在;购买数量是否正确;这是落单之前需要校验的问题;
        final ItemModel itemModel = itemService.getItem(itemId);
        if(ToolString.isBlank(itemModel)){
            return ToolClient.createJson(ConfigFile.code199,"购买的商品不存在");
        }
        final UserModel userModel = userService.getUserById(userId);
        if(ToolString.isBlank(userModel)){
            return ToolClient.createJson(ConfigFile.code199,"用户不合法!");
        }
        if(amount <= 0 || amount > 99){
            return ToolClient.createJson(ConfigFile.code199,"购买的数量信息不正确");
        }
        //校验商品秒杀活动信息
        if(promoId != null){
            // (1)、校验对应秒杀活动表内是否存在这个适用商品
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                return ToolClient.createJson(ConfigFile.code199,"商品秒杀活动信息不正确");
            // (2)、校验该秒杀活动是否正在进行中
            }else if(itemModel.getPromoModel().getStatus() != 2){
                return ToolClient.createJson(ConfigFile.code199,"该商品没有进行秒杀活动");
            }
        }
        //2、落单减库存[推荐,但有恶心下单的风险];还有一种就是支付减库存;
        final boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            return ToolClient.createJson(ConfigFile.code199,"库存不足!");
        }
        //3、订单入库;
        final OrderModel orderModel = new OrderModel();
        orderModel.setAmount(amount);
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        orderModel.setPromoId(promoId);
        if(promoId != null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());// 活动价,设置为秒杀活动的活动价，否则就是平销价
        }else {
            orderModel.setItemPrice(itemModel.getPrice());// 平销价
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));// orderModel.getItemPrice() 的set在上面两行代码
        //生成自定义规则的订单交易流水号
        orderModel.setId(generatorOrderCode());
        final OrderDO orderDO = convertFromOrderModel(orderModel);
        final int rows = orderDOMapper.insertSelective(orderDO);
        //商品销量的增加-->库存扣减成功且下单成功,当然这个也必须在事务内!
        itemService.increaseSales(itemId,amount);
        //4、返回前端;
        return ToolClient.createJson(rows,"恭喜,购买成功!","购买失败!");
    }

    private OrderDO convertFromOrderModel(final OrderModel orderModel){
        if(orderModel == null) return null;
        final OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());//类型不一致,因为数据库表的类型为Double,而OrderModel的类型是BigDecimal,所以要人肉的处理,如果数据库的类型是 decimal 则不需要处理的
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());//类型不一致,因为数据库表的类型为Double,而OrderModel的类型是BigDecimal,所以要人肉的处理,如果数据库的类型是 decimal 则不需要处理的
        return orderDO;
    }

    //自定义交易流水号|订单号的规则
    @Transactional(propagation = Propagation.REQUIRES_NEW)//由于上面的方法 createOrder()已打了Transactional标签,
    // 如果其中的insertSelective出错了,由于事务的回滚,那下一个事务拿到的值还是这个当前的值,
    // 所以要用Transactional(propagation = Propagation.REQUIRES_NEW)解决这个问题,它的作用就是即使外部开启了事务不管是否成功与否,本次调用都要消耗掉使用掉，即重新开启一个事务
    private String generatorOrderCode(){
        // 订单号有16位的;
        final StringBuilder stringBuilder = new StringBuilder();
        // 前8为为时间格式,可以根据时间来归档,可以通过订单的id的日期规则来分表;
        final LocalDateTime now = LocalDateTime.now();
        final String nowDate = now.format(DateTimeFormatter.ISO_DATE).replaceAll("-","");
        stringBuilder.append(nowDate);
        // 中间6为自增序列,由于使用的是Mysql,所以要自己实现自增序列的功能;
        long sequence = 0;
        final SequenceDO sequenceDO = sequenceDOMapper.getStepByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());//设置下一个当前值,本表还涉及考虑到最大值的情况
        //设置下一个当前值
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        //循环当前还欠几位
        for(int i = 0; i < 6 - sequenceStr.length(); i++){
            stringBuilder.append("0");
        }
        stringBuilder.append(sequenceStr);
        // 最后2位为分库分表位,即分库分表的路由信息,如:
        // Integer userId = 1000122;
        // int i = userId % 100;//因为userId是不変的,所以该userId都会往固定的库固定的表写入数据
        //此处暂时写死的,01
        stringBuilder.append("01");
        return stringBuilder.toString();
    }

}