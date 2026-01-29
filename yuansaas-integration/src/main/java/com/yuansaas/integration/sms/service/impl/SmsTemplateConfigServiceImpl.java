package com.yuansaas.integration.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.integration.sms.entity.SmsTemplateConfig;
import com.yuansaas.integration.sms.exception.SmsErrorCode;
import com.yuansaas.integration.sms.repository.SmsTemplateConfigRepository;
import com.yuansaas.integration.sms.service.SmsTemplateConfigService;
import com.yuansaas.integration.sms.vo.SmsTemplateVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * 短信模版实现类
 *
 * @author LXZ 2025/12/23 17:32
 */
@Service
@RequiredArgsConstructor
public class SmsTemplateConfigServiceImpl implements SmsTemplateConfigService {

    private final SmsTemplateConfigRepository smsTemplateConfigRepository;

    /**
     * 根据短信模板code查询
     *
     * @param templateCode 短信模板code
     * @author LXZ 2025/12/23  16:04
     */
    @Override
    public SmsTemplateVo findBySmsTemplateCode(String templateCode) {
        SmsTemplateConfig smsTemplateCode = smsTemplateConfigRepository.findByTemplateCodeAndLockStatus(templateCode, AppConstants.N);
        if (ObjectUtil.isEmpty(smsTemplateCode)) {
            throw  SmsErrorCode.SMS_TEMPLATE_NOT_FOUND.buildException();
        }
        return BeanUtil.copyProperties(smsTemplateCode, SmsTemplateVo.class);
    }
}
