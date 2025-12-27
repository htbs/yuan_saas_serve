package com.yuansaas.integration.sms.model;

import com.yuansaas.integration.sms.enums.SendTypeEnum;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Builder;
import lombok.Data;

/**
 *
 * 验证码参数model
 *
 * @author LXZ 2025/12/23 16:10
 */
@Data
@Builder
public class VerifyCodeModel {

    /**
     * 手机号
     */
    private String phone;
    /**
     * 模板编号
     */
    private String smsAliYunTemplateCode;
    /**
     * 类型
     */
    private SendTypeEnum types;
}
