package com.yuansaas.user.common.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 * @author HTB 2025/7/22 17:18
 */
@Getter
@AllArgsConstructor
public enum UserBaseRole implements IBaseEnum<UserBaseRole> {

    ADMIN("管理员"),
    USER("普通用户"),
    GUEST("访客");

    private final String description;

    @Override
    public String getName() {
        return this.name();
    }
}
