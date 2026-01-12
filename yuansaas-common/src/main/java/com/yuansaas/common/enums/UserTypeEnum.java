package com.yuansaas.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户类型
 *
 * @author HTB 2026/1/12 15:05
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum implements IBaseEnum<UserTypeEnum>{

    YUAN_SHI_USER("YUAN_SHI_USER", PlatFormEnum.YUAN_SHI,"元识总平台用户"),

    MERCHANT_USER("MERCHANT_USER", PlatFormEnum.MERCHANT,"商户平台用户"),

    CLIENT_USER("CLIENT_USER", PlatFormEnum.CLIENT,"客户端用户"),

    UNKNOWN("UNKNOWN", null,"未知用户"),
    ;


    private final String code;

    private final PlatFormEnum platForm;

    private final String description;

    /**
     * 根据String code获取类型枚举
     * @return 类型枚举
     */
    public static UserTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        return Arrays.stream(UserTypeEnum.values()).filter(item -> item.code.equals(code)).findFirst().orElse(null);
    }

    @Override
    public String getName() {
        return this.name();
    }
}
