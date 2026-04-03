package com.yuansaas.user.role.vo;

import com.yuansaas.user.role.enums.AuthorityEnum;
import com.yuansaas.user.role.enums.RoleTypeEnum;
import lombok.Data;

/**
 *
 * 详情
 *
 * @author LXZ 2025/10/21 10:59
 */
@Data
public class RoleVo {
    /**
     * 角色id
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标识
     * 枚举 {@link AuthorityEnum}
     */
    private String authorityType;
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
     * 部门id
     */
    private Long deptId;
    /**
     * 部门名称
     */
    private String deptName;
}
