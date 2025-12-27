package com.yuansaas.integration.sms.params;

import com.yuansaas.core.annotation.EnumValidate;
import com.yuansaas.integration.sms.enums.SendTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.io.Serializable;

/**
 * 手机号验证码
 * @author lxz 2021/11/19 14:50
 */
@Data
public class UpdatePhoneVerifyParam implements Serializable {

    /**
     * 发送的验证码
     */
    @NotEmpty(message = "验证码不能为空！")
    private String verifyCode;
    /**
     * 序列号
     */
    @NotEmpty(message = "序列号不能为空！")
    private String serialNo;
    /**
     * 用户手机号
     */
    @NotEmpty(message = "手机号不能为空！")
    private String phone;
    /**
     * 发送场景类型
     */
    @EnumValidate(enumClass = SendTypeEnum.class , message = "验证码类型必须为SendTypeEnum枚举里面的参数！")
    private SendTypeEnum sendSceneType;
}
