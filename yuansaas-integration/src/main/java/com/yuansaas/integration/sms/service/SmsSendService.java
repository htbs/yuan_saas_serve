package com.yuansaas.integration.sms.service;

import com.yuansaas.integration.sms.model.SmsRecordModel;
import com.yuansaas.integration.sms.params.SmsVerifyCodeSendParam;
import com.yuansaas.integration.sms.params.UpdatePhoneVerifyParam;
import com.yuansaas.integration.sms.vo.SendVerifyCodeVo;

/**
 *
 * 短信发送实现
 *
 * @author LXZ 2025/12/23 15:25
 */
public interface SmsSendService {




    /**
     * 保存短信发送记录
     * @param smsRecordModel 短信发送记录
     *
     * @author LXZ 2025/12/23 15:25
     */
    void saveSendLog(SmsRecordModel smsRecordModel);
}
