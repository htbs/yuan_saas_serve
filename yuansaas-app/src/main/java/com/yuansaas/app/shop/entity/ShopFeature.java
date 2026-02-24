package com.yuansaas.app.shop.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 *
 * 商铺功能关联表
 *
 * @author LXZ 2026/2/24 15:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shop_feature")
public class ShopFeature extends BaseEntity {
    /**
     * 商铺code
     */
    private String shopCode;
    /**
     * 功能code
     */
    private String featureCode;
    /**
     * 到期时间 （null 表示永久）
     */
    private LocalDateTime expireTime;
    /**
     * 锁定状态
     */
    private String lockStatus;
    /**
     * 来源
     * 枚举 {@link com.yuansaas.app.shop.enums.ShopFeatureSourceEnum}
     */
    private String source;
}
