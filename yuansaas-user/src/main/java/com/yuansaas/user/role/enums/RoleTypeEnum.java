package com.yuansaas.user.role.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色类型枚举
 * @author liuxuzhao
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum implements IBaseEnum<RoleTypeEnum> {

    /**
     * 内置角色
     */
    SYSTEM("内置角色"),
    /**
     * 自定义角色
     */
    CUSTOM("自定义角色");

    private final String description;

    @Override
    public String getName(){
        return this.name();
    }

}
