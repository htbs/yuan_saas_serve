package com.yuansaas.integration.sms.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信缓存枚举
 *
 * @author HTB 2026/1/23 10:05
 */
@Getter
@AllArgsConstructor
public enum SmsCacheEnum implements IBaseEnum<SmsCacheEnum> {

    SMS_LIMIT_PHONE("SMS:LIMIT:PHONE", "短信手机号限流key"),

    SMS_TOKEN("SMS:TOKEN" , "短信token"),

    SMS_NONCE("SMS:NONCE","短信防重")

    ;

    private final String key;
    private final String desc;

    @Override
    public String getName() {
        return this.name();
    }
}
