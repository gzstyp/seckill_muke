package com.fwtai.controller;

import com.fwtai.controller.viewobject.ItemVO;
import com.fwtai.response.BaseController;
import com.fwtai.response.CommonReturnType;
import com.fwtai.service.ItemService;
import com.fwtai.service.model.ItemModel;
import com.fwtai.tool.ConfigFile;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolString;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品服务
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-04-01 23:33
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@RestController("item")
@RequestMapping("/item")
// Spring boot 提供了可以跨越请求的注解,用该注解在跨域的操作session值是无法共享的,怎么办??,要定义域即可解决问题,DEFAULT_ALLOWED_HEADERS可以解决
// DEFAULT_ALLOWED_HEADERS 表示允许跨域传输所有header参数,将用于使用 token 放入header域
// 作为session的共享的跨域请求,它和跨域请求的ajax里的参数 xhrFields : {withCredentials:true} 相对应
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 创建商品,有CommonReturnType返回值
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 23:40
    */
    @RequestMapping(value = "/createItem",method = RequestMethod.POST)// http://127.0.0.1/item/createitem.html
    @ResponseBody
    public CommonReturnType createItem(
            @RequestParam (name = "title") String title,
            @RequestParam (name = "description") String description,
            @RequestParam (name = "price") BigDecimal price,
            @RequestParam (name = "stock") Integer stock,
            @RequestParam (name = "imgUrl") String imgUrl
    ) throws Exception{
        //封装service请求用来创建商品
        final ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        final ItemModel itemModelForReturn = itemService.createItem(itemModel);
        final ItemVO itemVO = this.convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    /**
     * 创建商品,无void返回值
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 23:40
    */
    @RequestMapping(value = "/create",method = RequestMethod.POST)// http://127.0.0.1/item/createitem.html
    @ResponseBody
    public void create(
            @RequestParam (name = "title") String title,
            @RequestParam (name = "description") String description,
            @RequestParam (name = "price") BigDecimal price,
            @RequestParam (name = "stock") Integer stock,
            @RequestParam (name = "imgUrl") String imgUrl,
            final HttpServletResponse response
    ){
        //封装service请求用来创建商品
        final ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        try {
            final String json = itemService.create(itemModel);
            ToolClient.responseJson(json,response);
        } catch (Exception e) {
            e.printStackTrace();
            ToolClient.createJsonException(response);
        }
    }

    /**
     * 把Model转换为VO
     * @param itemModel
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 23:53
    */
    private ItemVO convertVOFromModel(final ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        final ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        /**若有正在进行或将要进行秒杀活动的商品则设置状态为1或2,否则就设置没有秒杀活动是标识0*/
        if(itemModel.getPromoModel() != null){
            /** 表示有正在进行或将要进行秒杀活动的商品 */
            // {PromoModel的status[秒杀活动状态1表示活动还未开始;2表示秒杀活动进行中;3表示活动已结束]}
            // {ItemVO的promoStatus[对应的状态: 0表示该商品没有秒杀活动;1表示秒杀活动待开始;2表示商品秒杀活动正在进行中(正在进行秒杀活动则存在秒杀活动的价格吧!!)]}
            // 它们俩都一个 [1表示秒杀活动待开始;2表示商品秒杀活动正在进行中]
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());//商品秒杀活动的状态
            itemVO.setPromoId(itemModel.getPromoModel().getId());/*商品秒杀活动表的id主键*/
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));/**商品秒杀开始时间*/
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());/**商品秒杀的价格*/
        }else{
            /** 商品没有秒杀活动状态标识为0 */
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }

    /**
     * 商品详情页面浏览
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/1 23:40
    */
    @RequestMapping(value = "/get",method = RequestMethod.GET)// http://127.0.0.1/item/get.html
    @ResponseBody
    public void get(final HttpServletResponse response){
        final String id = this.request.getParameter("id");
        if(ToolString.isBlank(id)){
            ToolClient.responseJson(ToolClient.createJson(ConfigFile.code202,ConfigFile.msg202),response);
            return;
        }
        final ItemModel itemModel = itemService.getItem(Integer.parseInt(id));
        final ItemVO itemVO = this.convertVOFromModel(itemModel);
        ToolClient.responseJson(ToolClient.queryJson(itemVO),response);
    }

    /**
     * 商量列表浏览
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/4/2 1:28
    */
    @RequestMapping(value = "/listItem",method = RequestMethod.GET)// http://127.0.0.1/item/list.html
    @ResponseBody
    public void listItem(final HttpServletResponse response){
        final List<ItemModel> lists = itemService.listItem();
        final ArrayList<ItemVO> arrayList = new ArrayList<ItemVO>();
        for(int i = 0; i < lists.size(); i++){
            final ItemModel itemModel = lists.get(i);
            final ItemVO itemVO = this.convertVOFromModel(itemModel);
            arrayList.add(itemVO);
        }
        ToolClient.responseJson(ToolClient.queryJson(arrayList),response);
    }
}