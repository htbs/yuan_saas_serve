package com.yuansaas.app.order.shop.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 商家订单总数
 *
 * @author LXZ 2026/1/22 17:15
 */
@Data
public class ShopOrderCountVo implements Serializable {

    /**
     *  订单总数
     */
    private Long orderCount;
    /**
     * 金额 （分）
     */
    private Long amount;
}
