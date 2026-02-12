package com.yuansaas.app.feature.entity;

import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 功能表
 *
 * @author LXZ 2026/1/29 17:42
 */
@Data
@Entity
@Table(name = "feature")
public class Feature extends BaseEntity {
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
    private String lockStatus = AppConstants.N;
    /**
     * 删除状态
     */
    private String deleteStatus = AppConstants.N;
}
