package com.yuansaas.app.order.platform.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单类型
 *
 * @author LXZ 2026/1/18 14:42
 */
@Getter
@AllArgsConstructor
public enum OrderItemTypeEnum implements IBaseEnum<OrderItemTypeEnum> {

    FUNCTION_TEMPLATE("功能模版"),
    SMS_RECHARGE("短信充值"),


    ;

    private final String message;


    @Override
    public String getName() {
        return this.name();
    }
}
