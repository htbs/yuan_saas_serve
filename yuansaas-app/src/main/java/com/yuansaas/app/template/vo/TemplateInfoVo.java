package com.yuansaas.app.template.vo;

import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jpa.model.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * 模版配置分页列表vo
 *
 * @author LXZ 2026/2/3 17:48
 */
@Data
public class TemplateInfoVo extends BaseEntity {
    private Long id;
    /**
     * 行业类型
     */
    private String industryType;
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
    private String lockStatus = AppConstants.N;
    /**
     * 操作人
     */
    private String updateBy;
    /**
     * 操作时间
     */
    private LocalDateTime updateAt;
}
