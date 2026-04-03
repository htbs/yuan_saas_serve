package com.yuansaas.integration.sms.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 短信模版配置表
 *
 * @author LXZ 2025/12/23 15:44
 */
@Data
@Entity
@Table(name = "sms_template_config")
public class SmsTemplateConfig extends BaseEntity {
    /**
     * 模板编码
     */
    private String templateCode;
    /**
     * 模板名称
     */
    private String param;
    /**
     * 模板场景
     */
    private String scene;
    /**
     * 短信内容
     */
    private String smsContent;
    /**
     * 禁用状态
     */
    private String lockStatus;
    /**
     * 删除状态
     */
    private String deleteStatus;
}
