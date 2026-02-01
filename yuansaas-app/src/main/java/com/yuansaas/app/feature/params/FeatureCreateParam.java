package com.yuansaas.app.feature.params;

import com.yuansaas.app.feature.enums.FeatureScopeEnum;
import com.yuansaas.app.feature.enums.FeatureTypeEnum;
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
    private String name;
    /**
     * 功能描述
     */
    private String description;
    /**
     * 功能类型  默认类型 BUY
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureTypeEnum}
     */
    private String featureType = FeatureTypeEnum.BUY.getName();
    /**
     * 作用域  默认类型  ALL
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureScopeEnum}
     */
    private String featureScope = FeatureScopeEnum.ALL.getName();


}
