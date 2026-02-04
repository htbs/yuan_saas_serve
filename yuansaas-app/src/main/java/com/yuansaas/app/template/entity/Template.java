package com.yuansaas.app.template.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 模版配置表
 *
 * @author LXZ 2026/2/3 17:48
 */
@Data
@Entity
@Table(name = "template")
public class Template extends BaseEntity {
    /**
     * 模版code
     */
    private String templateCode;
    /**
     * 模版name
     */
    private String templateName;
    /**
     * 模版类型  H5/MINI/APP
     */
    private String templateType;
    /**
     * 套餐code
     */
    private String planCode;
    /**
     * 预览图
     */
    private String previewUrl;
    /**
     * 封面
     */
    private String coverImage;
    /**
     * 是否默认
     */
    private String isDefault;
    /**
     * 锁定状态
     */
    private String lockStatus;
    /**
     * 删除状态
     */
    private String deleteStatus;
}
