package com.yuansaas.integration.sms.api;

import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.integration.sms.model.CheckVerifyCodeModel;
import com.yuansaas.integration.sms.model.VerifyCodeModel;
import com.yuansaas.integration.sms.params.SmsVerifyCodeSendParam;
import com.yuansaas.integration.sms.params.UpdatePhoneVerifyParam;
import com.yuansaas.integration.sms.service.SmsVerifyService;
import com.yuansaas.integration.sms.vo.SendVerifyCodeVo;
import lombok.RequiredArgsConstructor;
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
     * 发送短信验证码
     * @param param 短信验证码发送参数
     * @author LXZ 2025/12/23  15:25
     */
    @RequestMapping(value = "/get/verify/code" , method = RequestMethod.POST)
    public ResponseEntity<ResponseModel<SendVerifyCodeVo>> getVerifyCode(@RequestBody @Validated SmsVerifyCodeSendParam param) {
        // 发送验证码
        return ResponseBuilder.okResponse(smsVerifyService.getVerifyCode(VerifyCodeModel.builder()
                .phone(param.getPhone())
                .types(param.getSendSceneType())
                .smsAliYunTemplateCode(param.getSmsTemplateCode()).build()));
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
}
