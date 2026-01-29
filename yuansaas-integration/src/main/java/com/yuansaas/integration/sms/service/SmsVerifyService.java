package com.yuansaas.integration.sms.service;

import com.yuansaas.integration.sms.model.CheckVerifyCodeModel;
import com.yuansaas.integration.sms.model.VerifyCodeModel;
import com.yuansaas.integration.sms.vo.SendVerifyCodeVo;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * 发送验证码实现
 *
 * @author LXZ 2025/12/23 16:08
 */
public interface SmsVerifyService {

    /**
     * 获取短信验证码的token
     */
    String getToken(String phone , HttpServletRequest httpServletRequest);

    /**
     * 获取短信验证码
     *
     * @param verifyCodeModel 验证码模型
     * @author LXZ 2025/12/23 16:08
     */
    SendVerifyCodeVo getVerifyCode (VerifyCodeModel verifyCodeModel , HttpServletRequest httpServletRequest);

    /**
     * 校验短信验证码
     * @param checkVerifyCodeModel 短信验证码校验入参
     *
     * @author LXZ 2025/12/23 16:08
     */
    Boolean checkVerifyCode(CheckVerifyCodeModel checkVerifyCodeModel);
}
