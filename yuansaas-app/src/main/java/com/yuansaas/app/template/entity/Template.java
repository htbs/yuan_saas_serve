package com.yuansaas.app.template.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.yuansaas.app.template.model.ImagesUrlModel;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;

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
     * 行业类型
     */
    private String industryType;
    /**
     * 预览图
     */
    @Type(value = JsonStringType.class)
    @Column(columnDefinition = "json")
    private List<ImagesUrlModel> imagesUrl;
    /**
     * 预览视频
     */
    private String videoUrl;
    /**
     * 封面
     */
    private String coverImage;
    /**
     * 是否默认
     */
    private String isDefault = AppConstants.N;
    /**
     * 锁定状态
     */
    private String lockStatus = AppConstants.N;
    /**
     * 删除状态
     */
    private String deleteStatus = AppConstants.N;
}
