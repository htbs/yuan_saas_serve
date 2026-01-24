package com.yuansaas.integration.sms.assist;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jackson.JacksonUtil;
import com.yuansaas.integration.common.enums.CallType;
import com.yuansaas.integration.common.enums.ServiceType;
import com.yuansaas.integration.common.log.annotations.ThirdPartyLog;
import com.yuansaas.integration.sms.exception.SmsErrorCode;
import com.yuansaas.integration.sms.model.SendSmsParamAliyunModel;
import com.yuansaas.integration.sms.properties.SmsProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 阿里云短信接口
 *
 * @see ：https://help.aliyun.com/document_detail/101414.html
 */
@Slf4j
@Component
@AllArgsConstructor
public class SmsAliYunAssist {

    private final SmsProperties smsProperties;
    private static volatile Client client;

    /**
     * 发送短信
     */
    @ThirdPartyLog(serviceName = ServiceType.ALI_SMS, action = "send", callType = CallType.SDK)
    public SendSmsResponseBody send(SendSmsParamAliyunModel sendSmsParamAliyunModel) {
        // 如果未打开发送短信开关，直接返回
        if (!smsProperties.isSendSmsEnable()) {
            log.info("短信发送功能已关闭，返回模拟成功响应");
            SendSmsResponseBody sendSmsResponseBody = new SendSmsResponseBody();
            sendSmsResponseBody.setCode(AppConstants.OK);
            sendSmsResponseBody.setBizId("");
            sendSmsResponseBody.setMessage(AppConstants.OK);
            sendSmsResponseBody.setRequestId("-1");
            return sendSmsResponseBody;
        }
        // 构建请求参数
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(sendSmsParamAliyunModel.getPhoneNumbers())
                .setSignName(sendSmsParamAliyunModel.getSignName())
                .setTemplateCode(sendSmsParamAliyunModel.getTemplateCode())
                .setTemplateParam(JacksonUtil.toJson(sendSmsParamAliyunModel.getTemplateParam()));
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = initClient().sendSms(sendSmsRequest);
            // 检查发送结果
            if (!AppConstants.OK.equals(sendSmsResponse.getBody().getCode())) {
                log.warn("短信发送失败，错误码: {}, 错误信息: {}",
                        sendSmsResponse.getBody().getCode(), sendSmsResponse.getBody().getMessage());
                throw SmsErrorCode.SMS_REQUEST_EXCEPTION.buildException(
                        "短信发送失败: " + sendSmsResponse.getBody().getMessage());
            }
            return sendSmsResponse.getBody();
        } catch (Exception e) {
            log.error("阿里云短信发送异常，手机号: {}，模板: {}，错误: {}",
                    sendSmsParamAliyunModel.getPhoneNumbers(),
                    sendSmsParamAliyunModel.getTemplateCode(),
                    e.getMessage(), e);
            throw SmsErrorCode.SMS_REQUEST_EXCEPTION.buildException();
        }
    }

    /**
     * 获取客户端
     */
    public Client getClient() throws Exception {
        if (client == null) {
            return initClient();
        }
        return client;
    }

    /**
     * 初始化客户端
     */
    private Client initClient() throws Exception {
        if (client == null) {
            log.info("正在初始化阿里云短信客户端");
            Config config = new Config()
                    .setAccessKeyId(smsProperties.getAliyun().getAccessKeyId())
                    .setAccessKeySecret(smsProperties.getAliyun().getAccessKeySecret());
            config.endpoint = smsProperties.getAliyun().getEndpoint();
            client = new Client(config);
            log.info("阿里云短信客户端初始化完成");
        }
        return client;
    }
}
