package com.yuansaas.app.order.platform.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * 平台订单
 *
 * @author LXZ 2026/1/18 14:30
 */
@Data
@Entity
@Table(name = "m_order")
public class Order extends BaseEntity {

    /**
     * 商铺编号
     */
    private  String shopCode;
    /**
     * 订单编号
     */
    private  Long orderNo;
    /**
     * 交易单号
     */
    private  String tradeNo;
    /**
     * 订单类型
     * @see com.yuansaas.app.order.platform.enums.OrderTypeEnum
     */
    private  String orderType;
    /**
     * 订单状态
     * @see com.yuansaas.app.order.platform.enums.OrderStatusEnum
     */
    private  String orderStatus;
    /**
     * 商品名称
     */
    private  String merchandiseNames;
    /**
     * 商品金额
     */
    private  Long merchandiseAmount;
    /**
     * '优惠后金额'
     */
    private  Long discountAmount;
    /**
     * '支付方式'
     * @see com.yuansaas.app.order.enums.PayChannelEnum
     */
    private  String payMethod;
    /**
     * '支付状态'
     * @see com.yuansaas.app.order.platform.enums.PayStatusEnum
     */
    private  String payStatus;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    /**
     * 支付金额
     */
    private  Long payAmount;
}
