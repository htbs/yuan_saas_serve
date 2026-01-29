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
public enum RoleCodeEnum  implements IBaseEnum<RoleCodeEnum> {

    SUPER_ADMIN("super_admin", "超级管理员"),
    TENANT_ADMIN("tenant_admin", "租户管理员"),
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
