package com.yuansaas.app.order.platform.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *
 *
 * @author LXZ 2026/1/18 16:33
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = "FUNCTION_TEMPLATE", value = FunctionTemplateModel.class),
        @JsonSubTypes.Type(name = "SMS_RECHARGE", value = SmsRechargeModel.class),
})
public interface MerchantOrderExtModel {

    /**
     * 订单类型 （功能 ， 短信）
     */
    String getType();
}
