package com.yuansaas.app.template.vo;

import com.yuansaas.app.template.model.ImagesUrlModel;
import com.yuansaas.common.constants.AppConstants;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * 模版配置分页列表vo
 *
 * @author LXZ 2026/2/3 17:48
 */
@Data
public class TemplatePageVo{
    private Long id;
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
