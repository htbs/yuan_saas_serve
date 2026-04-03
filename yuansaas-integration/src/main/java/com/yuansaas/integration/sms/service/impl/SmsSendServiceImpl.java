package com.yuansaas.integration.sms.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.integration.sms.entity.SmsSendLog;
import com.yuansaas.integration.sms.enums.SendTypeEnum;
import com.yuansaas.integration.sms.enums.SmsSceneEnum;
import com.yuansaas.integration.sms.model.SmsRecordModel;
import com.yuansaas.integration.sms.params.SmsVerifyCodeSendParam;
import com.yuansaas.integration.sms.params.UpdatePhoneVerifyParam;
import com.yuansaas.integration.sms.repository.SmsSendLogRepository;
import com.yuansaas.integration.sms.service.SmsSendService;
import com.yuansaas.integration.sms.service.SmsTemplateConfigService;
import com.yuansaas.integration.sms.vo.SendVerifyCodeVo;
import com.yuansaas.integration.sms.vo.SmsTemplateVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 短信发送实现类
 *
 * @author LXZ 2025/12/23 15:28
 */
@Service
@RequiredArgsConstructor
public class SmsSendServiceImpl implements SmsSendService {

    private final SmsTemplateConfigService smsTemplateConfigService;
    private final SmsSendLogRepository smsSendLogRepository;


    /**
     * 保存短信发送记录
     *
     * @param smsRecordModel 记录模型
     * @author LXZ 2025/12/23 15:25
     */
    @Override
    public void saveSendLog(SmsRecordModel smsRecordModel) {

        SmsSceneEnum smsSceneEnum = SmsSceneEnum.valueOf(smsRecordModel.getSmsTemplateConfig().getScene());

        SmsSendLog smsSendLog = new SmsSendLog();
        smsSendLog.setPhone(smsRecordModel.getPhone());
        smsSendLog.setContent(smsRecordModel.getContent());
        smsSendLog.setSmsType(smsSceneEnum);
        smsSendLog.setTemplateKey(smsRecordModel.getSmsTemplateConfig().getTemplateCode());
        smsSendLog.setRespCode(smsRecordModel.getSendSmsResponseBody().getCode());
        smsSendLog.setErrorMsg(smsRecordModel.getSendSmsResponseBody().getMessage());
        smsSendLog.setRequestId(smsRecordModel.getSendSmsResponseBody().getRequestId());
        smsSendLog.init();

        smsSendLogRepository.save(smsSendLog);
    }
}
