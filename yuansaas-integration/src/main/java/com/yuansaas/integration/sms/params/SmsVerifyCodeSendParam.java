package com.yuansaas.integration.sms.params;

import com.yuansaas.core.annotation.EnumValidate;
import com.yuansaas.integration.sms.enums.SendTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 短信验证码发送参数
 *
 * @author lxx 2025 12/27
 */
@Data
public class SmsVerifyCodeSendParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;
    /**
     * 模板编号
     * 0：默认模板
     */
    private String smsTemplateCode = "SMS_227263305";

    /**
     * 发送场景类型
     * @see SendTypeEnum
     */
    @EnumValidate(enumClass = SendTypeEnum.class, message = "发送类型错误")
    private SendTypeEnum sendSceneType;

    /**
     * 短信验证码的token
     */
    @NotBlank(message = "token不能为空")
    private String token;

    /**
     * 时间戳
     */
    @NotBlank(message = "时间戳不能为空")
    private Long timestamp;

    /**
     * 随机数，用于幂等
     */
    @NotBlank(message = "随机数不能为空")
    private String nonce;

    /**
     * 签名
     */
    @NotBlank(message = "签名不能为空")
    private String sign;


}
