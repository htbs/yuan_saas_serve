package com.yuansaas.app.plan.params;

import com.yuansaas.app.plan.enums.BillingTypeEnum;
import com.yuansaas.app.plan.enums.PlanTypeEnum;
import com.yuansaas.core.annotation.EnumValidate;
import com.yuansaas.core.page.PageModel;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * 查询套餐列表参数
 *
 * @author LXZ 2026/2/1 16:31
 */
@Data
public class FindPlanParam extends PageModel {
    /**
     * 套餐类型
     * 枚举 {@link com.yuansaas.app.plan.enums.PlanTypeEnum}
     */
    @EnumValidate(enumClass = PlanTypeEnum.class , message = "无效的套餐类型")
    private String planType;
    /**
     * 套餐name
     */
    private String planName;
    /**
     * 计费方式
     * 枚举 {@link com.yuansaas.app.plan.enums.BillingTypeEnum}
     */
    @EnumValidate(enumClass = BillingTypeEnum.class , message = "无效的计费方式")
    private String billingType;
    /**
     * 操作状态
     */
    private String lockStatus;

}
