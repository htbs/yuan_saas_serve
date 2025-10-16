package com.yuansaas.user.dept.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 部门管理
 *
 * @author LXZ 2025/10/16 16:53
 */
@Data
@Entity
@Table(name = "sys_dept")
public class SysDept extends BaseEntity {
    /**
     * 商家code
     */
    private Long merchantCode;
    /**
     * 上级id
     */
    private Long pid;
    /**
     * 部门名字
     */
    private String name;
    /**
     * 启用状态
     */
    private String lockStatus;
    /**
     * 删除状态
     */
    private String deleteStatus;
}
