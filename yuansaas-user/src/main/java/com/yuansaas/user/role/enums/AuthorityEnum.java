package com.yuansaas.user.role.enums;

import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色标识枚举
 * @author liuxuzhao
 */
@Getter
@AllArgsConstructor
public enum AuthorityEnum implements IBaseEnum<AuthorityEnum> {

    SUPER_ADMIN("super_admin", "超级管理员"),
    PLATFORM_ADMIN("platform_admin", "平台管理员"),
    TENANT_ADMIN("tenant_admin", "租户管理员"),
    ORDINARY_USER("ordinary_user", "普通员工"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String describe;



    public static boolean isSuperAdmin(String code) {
        return ObjectUtil.equals(code, SUPER_ADMIN.getCode());
    }

    @Override
    public String getName() {
        return this.name();
    }
}
