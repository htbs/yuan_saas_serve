package com.yuansaas.app.feature.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * 功能分页列表vo
 *
 * @author LXZ 2026/2/12 15:35
 */
@Data
public class FeaturePageListVo {

    private Long id;
    /**
     * 功能code
     */
    private String featureCode;
    /**
     * 功能name
     */
    private String featureName;
    /**
     * 功能描述
     */
    private String description;
    /**
     * 行业类型
     * 枚举 {@link com.yuansaas.app.common.enums.IndustryTypeEnum}
     */
    private String industryType;
    /**
     * 功能类型
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureTypeEnum}
     */
    private String featureType;
    /**
     * 作用范围
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureScopeEnum}
     */
    private String featureScope;
    /**
     * 锁定状态
     */
    private String lockStatus;
    /**
     * 操作人
     */
    private String updateBy;
    /**
     * 操作时间
     */
    private LocalDateTime updateAt;
}
