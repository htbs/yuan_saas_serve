package com.yuansaas.integration.sms.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * 发送类型
 *
 * @author LXZ 2025/12/23 15:19
 */
@Getter
@RequiredArgsConstructor
public enum SendTypeEnum implements IBaseEnum<SendTypeEnum> {

    /**
     * 注册
     */
    REG("注册"),
    /**
     * 修改
     */
    MODIFY("修改"),
    /**
     * 登录
     */
    LOGIN("登录"),
    /**
     * 认证
     */
    AUTHENTICATION("认证"),

    ;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
