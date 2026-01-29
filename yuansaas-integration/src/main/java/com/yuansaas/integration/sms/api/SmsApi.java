package com.yuansaas.integration.sms.api;

import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.integration.sms.model.CheckVerifyCodeModel;
import com.yuansaas.integration.sms.model.VerifyCodeModel;
import com.yuansaas.integration.sms.params.SmsVerifyCodeSendParam;
import com.yuansaas.integration.sms.params.UpdatePhoneVerifyParam;
import com.yuansaas.integration.sms.service.SmsVerifyService;
import com.yuansaas.integration.sms.vo.SendVerifyCodeVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 短信发送
 *
 * @author LXZ 2025/12/23 14:52
 */
@RequestMapping(value = "/sms")
@RequiredArgsConstructor
@RestController
public class SmsApi {

    private final SmsVerifyService smsVerifyService;


    /**
     * 获取短信验证码token
     *
     * @apiNote 该token 在发送短信之前调用， 并且只能使用一次，有效期60s ,为了防止短信盗刷、幂等性等
     */
    @RequestMapping(value = "/token/{phone}" , method = RequestMethod.GET)
    public ResponseModel<String> getToken(@PathVariable(value = "phone" ) String phone , HttpServletRequest httpServletRequest) {
        return ResponseBuilder.success(smsVerifyService.getToken(phone , httpServletRequest));
    }

    /**
     * 发送短信验证码
     * @param param 短信验证码发送参数
     * @author LXZ 2025/12/23  15:25
     */
    @RequestMapping(value = "/get/verify/code" , method = RequestMethod.POST)
    public ResponseEntity<ResponseModel<SendVerifyCodeVo>> getVerifyCode(@RequestBody @Validated SmsVerifyCodeSendParam param , HttpServletRequest httpServletRequest) {
        // 发送验证码
        return ResponseBuilder.okResponse(smsVerifyService.getVerifyCode(VerifyCodeModel.builder()
                .phone(param.getPhone())
                .types(param.getSendSceneType())
                .smsAliYunTemplateCode(param.getSmsTemplateCode())
                        .nonce(param.getNonce())
                        .sign(param.getSign())
                        .timestamp(param.getTimestamp())
                        .token(param.getToken())
                .build() , httpServletRequest));
    }

    /**
     * 验证码校验
     * @param updatePhoneVerifyParam 校验参数
     * @author LXZ 2025/12/23  15:24
     */
    @RequestMapping(value = "/check/verify/code" , method = RequestMethod.POST)
    public ResponseModel<Boolean> checkVerifyCode(@RequestBody @Validated UpdatePhoneVerifyParam updatePhoneVerifyParam) {
        return ResponseBuilder.success(smsVerifyService.checkVerifyCode(CheckVerifyCodeModel.builder()
                        .phone(updatePhoneVerifyParam.getPhone())
                        .verifyContent(updatePhoneVerifyParam.getVerifyCode())
                        .serialNo(updatePhoneVerifyParam.getSerialNo())
                        .type(updatePhoneVerifyParam.getSendSceneType())
                .build()));
    }

    /**
     * 校验手机号
     * @param phone 手机号
     * @return true/false
     */
    private boolean isValidPhone(String phone) {
        // 手机号正则验证
        return StringUtils.isNotBlank(phone) && phone.matches("^1[3-9]\\d{9}$");
    }


}
