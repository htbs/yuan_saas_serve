package com.yuansaas.app.order.platform.model;

import com.yuansaas.app.order.platform.enums.OrderItemTypeEnum;
import lombok.Data;

/**
 *
 * 子订单模版
 *
 * @author LXZ 2026/1/18 17:17
 */
@Data
public class OrderItemModel {
    /**
     * 子项类型
     * @see com.yuansaas.app.order.platform.enums.OrderItemTypeEnum
     */
    private OrderItemTypeEnum type;
    /**
     * 商品名字
     */
    private String merchandiseName;
    /**
     * 商品金额
     */
    private Integer merchandiseAmount;
    /**
     * 商品扩展信息
     */
    private MerchantOrderExtModel merchantOrderExtModel;



}
