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
 * @author cqq 2024/9/9 15:40
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

}
