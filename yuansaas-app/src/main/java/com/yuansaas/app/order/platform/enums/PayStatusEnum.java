package com.yuansaas.app.order.platform.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态枚举
 * @author LXZ 2026/1/18  15:26
 */
@AllArgsConstructor
@Getter
public enum PayStatusEnum implements IBaseEnum<PayStatusEnum> {

    /**
     * 待支付
     */
    WAIT_PAY("待支付"),

    /**
     * 支付成功
     */
    SUCCESS("支付成功"),

    /**
     * 支付失败
     */
    FAILD("支付失败");
    /**
     * 描述
     */
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
