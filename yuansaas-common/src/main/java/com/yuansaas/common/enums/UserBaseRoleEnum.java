package com.yuansaas.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户基础角色
 *
 * @author HTB 2026/1/12 15:43
 */
@Getter
@AllArgsConstructor
public enum UserBaseRoleEnum implements IBaseEnum<UserBaseRoleEnum>{

    LOGIN_USER("登录用户"),
    GUEST("访客"),

    ;

    private final String description;

    /**
     * 根据String code获取类型枚举
     * @return 类型枚举
     */
    public static UserBaseRoleEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
       return Arrays.stream(UserBaseRoleEnum.values()).filter(e -> e.name().equals(code)).findFirst().orElse(null);
    }
    @Override
    public String getName() {
        return this.name();
    }
}
