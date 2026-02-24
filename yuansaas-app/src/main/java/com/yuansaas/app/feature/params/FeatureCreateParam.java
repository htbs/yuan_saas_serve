package com.yuansaas.app.feature.params;

import com.yuansaas.app.common.enums.IndustryTypeEnum;
import com.yuansaas.app.feature.enums.FeatureScopeEnum;
import com.yuansaas.app.feature.enums.FeatureTypeEnum;
import com.yuansaas.core.annotation.EnumValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 功能保存参数
 *
 * @author LXZ 2026/1/29 17:30
 */
@Data
public class FeatureCreateParam implements Serializable {

    /**
     * 功能名字
     */
    @Size(min = 2, max = 30, message = "功能名字长度为 4-30 个字符")
    @NotBlank(message = "功能名字不能为空")
    private String featureName;
    /**
     * 功能描述
     */
    private String description;
    /**
     * 功能类型  默认类型 DEFAULT
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureTypeEnum}
     */
    @EnumValidate(enumClass = FeatureTypeEnum.class , message = "无效的功能类型")
    private String featureType = FeatureTypeEnum.DEFAULT.getName();
    /**
     * 作用域  默认类型  ALL
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureScopeEnum}
     */
    @EnumValidate(enumClass = FeatureTypeEnum.class , message = "无效的作用域类型")
    private String featureScope = FeatureScopeEnum.ALL.getName();
    /**
     * 行业类型
     * 枚举 {@link com.yuansaas.app.common.enums.IndustryTypeEnum}
     */
    @EnumValidate(enumClass = IndustryTypeEnum.class , message = "行业类型枚举无效")
    private String industryType;


}
