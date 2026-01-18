package com.yuansaas.app.order.platform.vo;

import com.yuansaas.app.order.platform.model.MerchantOrderExtModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * 查询订单详请信息
 *
 * @author LXZ 2026/1/18 15:31
 */
@Data
public class OrderItemDataVo {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 商品类型
     */
    private String type;
    /**
     * 商品名字
     */
    private String merchandiseName;
    /**
     * 单价
     */
    private String merchandiseAmount;

    /**
     * 扩展字段
     */
    private MerchantOrderExtModel merchantOrderExt;


}
