package com.yuansaas.app.template.vo;

import com.yuansaas.common.constants.AppConstants;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * 模版功能列表分页vo
 *
 * @author LXZ 2026/2/9 17:38
 */
@Data
public class TemplateFeaturePageVo {

    /**
     * 关联id
     */
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
     * 锁定状态
     */
    private String lockStatus = AppConstants.N;
    /**
     * 创建时间
     */
    private LocalDateTime createAt;
}
