package com.yuansaas.app.feature.params;

import com.yuansaas.app.feature.enums.FeatureScopeEnum;
import com.yuansaas.app.feature.enums.FeatureTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class FeatureUpdateParam implements Serializable {

    /**
     * 功能id
     */
    @NotNull(message = "功能id不能为空")
    private Long id;
    /**
     * 功能名字
     */
    @Size(min = 2, max = 30, message = "功能名字长度为 4-30 个字符")
    @NotBlank(message = "功能名字不能为空")
    private String name;
    /**
     * 功能描述
     */
    private String description;

    /**
     * 功能类型  默认类型 BUY
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureTypeEnum}
     */
    private String featureType ;
    /**
     * 作用域  默认类型  ALL
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureScopeEnum}
     */
    private String featureScope;

    /**
     * 行业类型
     * 枚举 {@link com.yuansaas.app.common.enums.IndustryTypeEnum}
     */
    private String industryType;

}
