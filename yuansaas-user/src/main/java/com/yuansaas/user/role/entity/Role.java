package com.yuansaas.user.role.entity;

import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jpa.model.BaseEntity;
import com.yuansaas.user.role.enums.RoleCodeEnum;
import com.yuansaas.user.role.enums.RoleTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 角色
 *
 * @author LXZ 2025/10/21 10:28
 */
@Data
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {
    /**
     * 商家编码
     */
    private String shopCode;
    /**
     * '部门ID
     */
    private Long deptId;
    /**
     * '角色名称'
     */
    private String name;
    /**
     * 角色标识
     * 枚举 {@link RoleCodeEnum}
     */
    private String code;
    /**
     * 角色类型
     * 枚举 {@link RoleTypeEnum}
     */
    private String type;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否启用(N 启用 、 Y 禁用 )
     */
    private String lockStatus = AppConstants.N;
    /**
     * 是否删除(N 不删除 、 Y 删除)
     */
    private String deleteStatus = AppConstants.N;
}
