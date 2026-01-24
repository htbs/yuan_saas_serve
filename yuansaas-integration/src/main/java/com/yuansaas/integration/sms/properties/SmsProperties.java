package com.yuansaas.integration.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云发送短信配置
 * @author LXZ 2025/12/23  16:33
 */
@Data
@Component
@ConfigurationProperties("sms")
public class SmsProperties {

    /**
     * 发送短信内容的间隔时间 （秒）单位
     */
    private int smsSendIntervalMin = 5 * 60;

    /**
     * 本地发送短信时密钥
     */
    private String secretKey;

    /**
     * 阿里云短信配置
     */
    private AliyunProperties aliyun;

    /**
     * 是否开启短信发送功能
     */
    private boolean sendSmsEnable = true;
    /**
     * code长度
     */
    private int codeLength = 6;
    /**
     * 验证码过期时间（秒）
     */
    private int codeExpireTime = 60 * 5;
    /**
     * 是否显示验证码
     */
    private boolean show = false;

    /**
     * 阿里云短信配置
     */
    @Data
    public static class AliyunProperties {

        private String accessKeyId;

        private String accessKeySecret;

        private String endpoint;
    }

}
