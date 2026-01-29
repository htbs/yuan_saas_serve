package com.yuansaas.integration.sms.model;

import com.yuansaas.integration.sms.enums.SendTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码校验入参
 *
 * @author rlf 2023/10/24 10:54
 */
@Data
@Builder
public class CheckVerifyCodeModel {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 短信内容
     */
    private String verifyContent;
    /**
     * 序列号
     */
    private String serialNo;
    /**
     * 发送类型
     */
    private SendTypeEnum type;
}
