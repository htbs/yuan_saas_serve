package com.yuansaas.app.order.platform.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单分页列表vo
 *
 * @author LXZ 2026/1/18 15:02
 */
@Data
public class OrderPageListVo {
    /**
     * id
     */
    private Long id;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 订单金额
     */
    private Integer orderAmount;
    /**
     * 商铺code
     */
    private String shopCode;
    /**
     * 商铺名字
     */
    private String shopName;
    /**
     * 创建时间
     */
    private LocalDateTime createAt;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 订单状态
     */
    private String orderStatus;
}
