package com.yuansaas.app.plan.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 套餐表
 *
 * @author LXZ 2026/2/1 15:49
 */
@Data
@Entity
@Table(name = "plan")
public class Plan extends BaseEntity {

    /**
     * 套餐类型
     */
    private String planType;
    /**
     * 套餐code
     */
    private String planCode;
    /**
     * 套餐name
     */
    private String planName;
    /**
     * 套餐描述
     */
    private String description;
    /**
     * 计费方式
     */
    private String billingType;
    /**
     * 有效数量
     */
    private Integer validCount;
    /**
     * 数量单位
     */
    private String countUnit;
    /**
     * 套餐金额 （分）
     */
    private Long planAmount;
    /**
     * 锁定状态
     */
    private String lockStatus;
    /**
     * 删除状态
     */
    private String deleteStatus;
}
