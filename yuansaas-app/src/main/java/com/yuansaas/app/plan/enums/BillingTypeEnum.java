package com.yuansaas.app.plan.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 计费类型
 *
 * @author LXZ 2026/2/1 15:56
 */
@Getter
@AllArgsConstructor
public enum BillingTypeEnum implements IBaseEnum<BillingTypeEnum> {

    FREE("免费"),
    SUBSCRIBE("订阅 （天 ， 月 ， 季度 ， 年）"),
    RESERVE("一次性买断"),
    MARKETING("按量 ('短信 ， 直播 ， ai ....')"),

    ;

    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
