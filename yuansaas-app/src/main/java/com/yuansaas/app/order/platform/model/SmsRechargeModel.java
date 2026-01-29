package com.yuansaas.app.order.platform.model;

import lombok.Data;

/**
 * 短信充值模版model
 *
 * @author LXZ 2026/1/18 16:45
 */
@Data
public class SmsRechargeModel implements MerchantOrderExtModel {

    private String type = "SMS_RECHARGE" ;
    /**
     * 购买条数
     */
    private Integer count;



}
