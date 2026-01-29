package com.yuansaas.app.shop.model;

import com.yuansaas.app.order.enums.PayChannelEnum;
import com.yuansaas.app.shop.entity.Shop;
import lombok.Builder;
import lombok.Data;

/**
 *
 * 商铺激活初始化model
 *
 * @author LXZ 2026/1/28 12:30
 */
@Data
@Builder
public class ShopInitModel {

    /**
     * 商品code
     */
    private Shop shop;
    /**
     * 支付金额 （分）
     */
    private Long payAmount;
    /**
     * 支付渠道
     * @see PayChannelEnum
     */
    private PayChannelEnum payChannel;
}
