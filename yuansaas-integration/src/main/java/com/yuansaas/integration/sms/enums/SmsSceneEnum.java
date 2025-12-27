package com.yuansaas.integration.sms.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信场景枚举
 *
 * @author LXZ 2025/12/23 15:19
 */
@Getter
@AllArgsConstructor
public enum SmsSceneEnum implements IBaseEnum<SmsSceneEnum> {

    /**
     * 短信通知
     */
    SMS_NOTICE("短信通知"),

    /**
     * 验证码
     */
    SMS_VALID_CODE("验证码"),

    ;

    private String desc;


    @Override
    public String getName() {
        return this.name();
    }
}
