package com.yuansaas.app.order.platform.model;

import com.yuansaas.app.order.enums.PayChannelEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * 订单支付参数
 *
 * @author LXZ 2026/1/19 16:44
 */
@Data
public class OrderPayParam {

    /**
     * 订单编号
     */
    private Long orderNo;
    /**
     * 交易单号
     */
    private String tradeNo;
    /**
     * 支付金额
     */
    private Long payAmount;
    /**
     * 支付渠道
     * @see PayChannelEnum
     */
    private PayChannelEnum payChannel;
    /**
     * 支付时间
     */
    private LocalDateTime paySucceededTime;
}
