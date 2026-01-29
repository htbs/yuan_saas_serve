package com.yuansaas.integration.sms.service;

import com.yuansaas.integration.sms.vo.SmsTemplateVo;

/**
 *
 * 短信模版实现
 *
 * @author LXZ 2025/12/23 16:00
 */
public interface SmsTemplateConfigService {

    /**
     * 根据短信模板code查询
     * @param templateCode 短信模板code
     * @author LXZ 2025/12/23  16:04
     */
    SmsTemplateVo findBySmsTemplateCode(String templateCode);
}
