package com.yuansaas.app.plan.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 *
 * 套餐 - 功能关系表
 *
 * @author LXZ 2026/2/1 15:49
 */
@Data
@Entity
@Table(name = "plan_feature_link")
public class PlanFeatureLink extends BaseEntity {

    /**
     * 主键ID
     */
    @Id
    @GenericGenerator(
            name = "id",
            strategy = "com.yuansaas.core.jpa.id.CustomIdentityGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "id"
    )
    private Long id;
    /**
     * 套餐code
     */
    private String planCode;
    /**
     * 功能code
     */
    private String featureCode;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createAt;
    /**
     * 备注
     */
    private String remark;

}
