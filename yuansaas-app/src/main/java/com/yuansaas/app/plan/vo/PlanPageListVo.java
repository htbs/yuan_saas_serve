package com.yuansaas.app.plan.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * 套餐分页列表vo
 *
 * @author LXZ 2026/2/1 16:35
 */
@Data
public class PlanPageListVo implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 套餐类型
     */
    private String planType;
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
    /**
     * 操作人
     */
    private String updateBy;
    /**
     * 操作时间
     */
    private LocalDateTime updateAt;
}
