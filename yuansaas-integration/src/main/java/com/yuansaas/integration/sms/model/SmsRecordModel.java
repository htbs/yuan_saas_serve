package com.yuansaas.integration.sms.model;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.yuansaas.integration.sms.vo.SmsTemplateVo;
import lombok.Builder;
import lombok.Data;

/**
 * 短信记录保存model
 *
 * @author lxz 2025/12/23 18:12
 */
@Data
@Builder
public class SmsRecordModel {

    /**
     * 发短信响应体
     */
    private SendSmsResponseBody sendSmsResponseBody;

    /**
     * 短信模版信息
     */
    private SmsTemplateVo smsTemplateConfig;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信内容
     */
    private String content;

}
