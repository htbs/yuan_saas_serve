package com.yuansaas.integration.sms.vo;

import com.yuansaas.common.constants.AppConstants;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 发送验证码vo
 *
 * @author LXZ 2025/12/23 15:05
 */
@Data
@Builder
public class SendVerifyCodeVo implements Serializable {

    /**
     * code
     */
    private String code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String verifyCode;
    /**
     * 序列号
     */
    private String serialNo;

    public boolean isSuccess(){
        return AppConstants.OK.equalsIgnoreCase(this.getCode())
                || AppConstants.SUCCESS.equalsIgnoreCase(this.getCode());
    }
}
