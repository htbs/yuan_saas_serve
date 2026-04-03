package com.yuansaas.app.plan.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 分配功能给套餐的参数
 *
 * @author LXZ 2026/2/2 11:07
 */
@Data
public class AssignPlanFeatureParam implements Serializable {

    /**
     * 套餐code
     */
    @NotBlank(message = "套餐code不能为空")
    private String planCode;
    /**
     * 功能code
     */
    @NotEmpty(message = "功能code不能为空")
    private List<String> featureCode;
}
