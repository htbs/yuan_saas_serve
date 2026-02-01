package com.yuansaas.app.plan.params;

import com.yuansaas.app.plan.enums.BillingTypeEnum;
import com.yuansaas.app.plan.enums.PlanTypeEnum;
import com.yuansaas.core.annotation.EnumValidate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 保存套餐参数
 *
 * @author LXZ 2026/2/1 16:21
 */
@Data
public class PlanUpdateParam implements Serializable {

    @NotNull(message = "套餐id不能为空")
    private Long id;
    /**
     * 套餐类型
     * 枚举 {@link PlanTypeEnum}
     */
    @EnumValidate(enumClass = PlanTypeEnum.class , message = "无效的套餐类型")
    private String planType;
    /**
     * 套餐name
     */
    @Size(min = 2 , max = 40 , message = "套餐名字长度为2~40个字符")
    private String planName;
    /**
     * 套餐描述
     */
    private String description;
    /**
     * 计费方式
     * 枚举 {@link BillingTypeEnum}
     */
    @EnumValidate(enumClass = BillingTypeEnum.class , message = "无效的计费方式")
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
}
