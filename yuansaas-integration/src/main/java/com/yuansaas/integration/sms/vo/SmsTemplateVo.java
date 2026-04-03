package com.yuansaas.integration.sms.vo;

import lombok.Data;

/**
 *
 * 短信模版
 *
 * @author LXZ 2025/12/23 16:01
 */
@Data
public class SmsTemplateVo {
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
