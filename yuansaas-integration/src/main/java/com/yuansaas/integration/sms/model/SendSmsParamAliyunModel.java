package com.yuansaas.integration.sms.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 发送阿里云短信参数
 * @see：https://help.aliyun.com/document_detail/101414.html
 */
@Data
@Accessors(chain = true)
public class SendSmsParamAliyunModel {

    /**
     * 手机号
     */
    private String phoneNumbers;

    /**
     * 签名
     */
    private String signName;

    /**
     * 短息模版ID
     */
    private String templateCode;

    /**
     * 短信模板变量对应的实际值
     */
    private Map<String , Object> templateParam;

}
