package com.yuansaas.app.feature.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 功能菜单关系表
 *
 * @author LXZ 2026/2/1 14:17
 */
@Data
@Entity
@Table(name = "feature_menu_link")
public class FeatureMenuLink extends BaseEntity {
    /**
     * 功能code
     */
    private String featureCode;
    /**
     * 菜单code
     */
    private String menuCode;
    /**
     * 锁定状态
     */
    private String lockStatus;
}
