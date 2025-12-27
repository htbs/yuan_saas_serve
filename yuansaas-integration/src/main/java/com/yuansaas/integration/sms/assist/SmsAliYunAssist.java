package com.yuansaas.integration.sms.assist;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jackson.JacksonUtil;
import com.yuansaas.integration.sms.exception.SmsErrorCode;
import com.yuansaas.integration.sms.model.SendSmsParamAliyunModel;
import com.yuansaas.integration.sms.properties.SmsProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 阿里云短信接口
 * @see：https://help.aliyun.com/document_detail/101414.html
 * @author lxz 2025/12/23 17:00
 */
@Slf4j
@Component
@AllArgsConstructor
public class SmsAliYunAssist {

    private final SmsProperties smsProperties;
    private static volatile Client client;
    private final static Byte LOCK = 0;

    /**
     * 发送短信
     * @param sendSmsParamAliyunModel 发送阿里云短信参数
     *
     * @author lxz 2025/12/23 17:00
     */
    public SendSmsResponseBody send(SendSmsParamAliyunModel sendSmsParamAliyunModel) {
        // 如果未打开发送短信开关，直接返回
        if(!smsProperties.isSendSmsEnable()){
            SendSmsResponseBody sendSmsResponseBody = new SendSmsResponseBody();
            sendSmsResponseBody.setCode(AppConstants.OK);
            sendSmsResponseBody.setBizId("");
            sendSmsResponseBody.setMessage(AppConstants.OK);
            sendSmsResponseBody.setRequestId("-1");
            return sendSmsResponseBody;
        }
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(sendSmsParamAliyunModel.getPhoneNumbers())
                .setSignName(sendSmsParamAliyunModel.getSignName())
                .setTemplateCode(sendSmsParamAliyunModel.getTemplateCode())
                .setTemplateParam(JacksonUtil.toJson(sendSmsParamAliyunModel.getTemplateParam()));
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = initClient().sendSms(sendSmsRequest);
        } catch (Exception e) {
            log.error("阿里云短信发送失败", e);
            throw  SmsErrorCode.SMS_REQUEST_EXCEPTION.buildException();
        }
        return sendSmsResponse.getBody();
    }

    /**
     * 获取客户端
     * @author lxz 2025/12/23 17:00
     */
    public Client getClient() throws Exception {
        if(client == null){
            return initClient();
        }
        return client;
    }

    /**
     * 初始化客户端
     *
     * @author lxz 2025/12/23 17:00
     */
    private Client initClient() throws Exception {
        synchronized (LOCK){
            if(client == null){
                Config config = new Config()
                        .setAccessKeyId(smsProperties.getAliyun().getAccessKeyId())
                        .setAccessKeySecret(smsProperties.getAliyun().getAccessKeySecret());
                config.endpoint = smsProperties.getAliyun().getEndpoint();
                client = new Client(config);
            }
        }
        return client;
    }
}
