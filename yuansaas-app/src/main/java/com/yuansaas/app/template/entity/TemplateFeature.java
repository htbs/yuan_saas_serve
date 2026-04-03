package com.yuansaas.app.template.entity;

import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 模版功能配置关系表
 *
 * @author LXZ 2026/2/3 17:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "template_feature")
public class TemplateFeature extends BaseEntity {
    /**
     * 模版code
     */
    private String templateCode;
    /**
     * 功能code
     */
    private String featureCode;
    /**
     * 是否默认 Y 默认 | N 不默认
     */
    private String isDefault;
    /**
     * 锁定状态
     */
    private String lockStatus = AppConstants.N;

}
