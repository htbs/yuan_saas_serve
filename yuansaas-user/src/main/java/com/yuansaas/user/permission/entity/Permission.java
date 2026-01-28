package com.yuansaas.user.permission.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 权限点配置表
 *
 * @author LXZ 2026/1/27 12:11
 */
@Data
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    /**
     * 权限code
     */
    private String permissionCode;
    /**
     * 权限名字
     */
    private String permissionName;
    /**
     * 描述
     */
    private String description;
}
