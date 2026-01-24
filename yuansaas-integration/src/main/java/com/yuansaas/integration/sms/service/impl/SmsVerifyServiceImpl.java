package com.yuansaas.integration.sms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextHolder;
import com.yuansaas.core.redis.RedisUtil;
import com.yuansaas.core.utils.RandomUtil;
import com.yuansaas.core.utils.SignUtil;
import com.yuansaas.integration.sms.assist.SmsAliYunAssist;
import com.yuansaas.integration.sms.entity.SmsVerifyCode;
import com.yuansaas.integration.sms.enums.SmsCacheEnum;
import com.yuansaas.integration.sms.exception.SmsErrorCode;
import com.yuansaas.integration.sms.model.CheckVerifyCodeModel;
import com.yuansaas.integration.sms.model.VerifyCodeModel;
import com.yuansaas.integration.sms.properties.SmsProperties;
import com.yuansaas.integration.sms.repository.SmsVerifyCodeRepository;
import com.yuansaas.integration.sms.service.SmsTemplateConfigService;
import com.yuansaas.integration.sms.service.SmsVerifyService;
import com.yuansaas.integration.sms.vo.SendVerifyCodeVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 *
 * 发送验证码实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsVerifyServiceImpl implements SmsVerifyService {

    private final SmsVerifyCodeRepository smsVerifyCodeRepository;
    private final SmsProperties smsProperties;
    private final SmsAliYunAssist smsAliyunAssist;
    private final SmsTemplateConfigService smsTemplateConfigService;
    // 验证码有效期限（秒）
    private static final int SMS_EXPIRE_TIME = 60;


    @Override
    public String getToken(String phone , HttpServletRequest request) {
        String ip = AppContextHolder.getIpAddress().orElse("127.0.0.1");
        String ua = request.getHeader("User-Agent");

        // 1. 手机号限频（示例：60s 内 1 次）
        String phoneKey = RedisUtil.genKey(SmsCacheEnum.SMS_LIMIT_PHONE, phone);

        if (Boolean.TRUE.equals(RedisUtil.hasKey(phoneKey))) {
            throw  SmsErrorCode.SMS_10001.buildException();
        }
        RedisUtil.set(phoneKey, "1", Duration.ofSeconds(SMS_EXPIRE_TIME));

        // 2. 生成 token
        String token =RandomUtil.generateSimpleUuid();

        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("phone", phone);
        tokenData.put("ip", ip);
        tokenData.put("ua", ua);
        String tokenKey = RedisUtil.genKey(SmsCacheEnum.SMS_TOKEN, token);
        RedisUtil.set(tokenKey ,tokenData , SMS_EXPIRE_TIME , TimeUnit.SECONDS);
        return token;
    }

    /**
     * 获取短信验证码
     *
     * @param verifyCodeModel 验证码模型
     * @author LXZ 2025/12/23 16:08
     */
    @Override
    @Transactional
    public SendVerifyCodeVo getVerifyCode(VerifyCodeModel verifyCodeModel , HttpServletRequest httpServletRequest) {
        verifySign(verifyCodeModel, httpServletRequest);
        // 验证模版是否存在
        smsTemplateConfigService.findBySmsTemplateCode(verifyCodeModel.getSmsAliYunTemplateCode());
        // 1分钟内不能重复获取验证码
//        SmsVerifyCode smsVerifyCode = smsVerifyCodeRepository.getSmsVerifyCodeByPhoneAndTypes(verifyCodeModel.getPhone(), verifyCodeModel.getTypes().name());
//        if (ObjectUtil.isNotEmpty(smsVerifyCode)) {
//            if(LocalDateTime.now().isBefore(smsVerifyCode.getCreateAt().plusSeconds(smsProperties.getSmsSendIntervalMin()))){
//              throw  SmsErrorCode.SMS_10001.buildException( smsProperties.getSmsSendIntervalMin() / AppConstants.DEFAULT_KEEP_ALIVE_SECONDS + "分钟内只能发送一次,请稍候再试");
//            }
//        }
        // 生成验证码
        SmsVerifyCode verifyCode = new SmsVerifyCode();
        verifyCode.setPhone(verifyCodeModel.getPhone());
        verifyCode.setType(verifyCodeModel.getTypes().name());
        verifyCode.setVerifyContent(RandomUtil.generateRandomNumberString(smsProperties.getCodeLength()));
        verifyCode.setSerialNo(RandomUtil.generateTimestampId());
        verifyCode.setExpiredAt(LocalDateTime.now().plusSeconds(SMS_EXPIRE_TIME));
        verifyCode.setStatus(AppConstants.N);
        verifyCode.init();

        // 保存验证码
        smsVerifyCodeRepository.save(verifyCode);

        // 发送验证码
        try {
//            Map<String , Object> param = new HashMap<>();
//            param.put("k1" , verifyCode.getVerifyContent());
//            SendSmsParamAliyunModel sendSmsParamAliyunModel =  new SendSmsParamAliyunModel()
//                    .setTemplateParam(param)
//                    .setTemplateCode(verifyCodeModel.getSmsAliYunTemplateCode())
//                    .setSignName("医链健康")
//                    .setPhoneNumbers(verifyCode.getPhone());
//            SendSmsResponseBody sendSmsResponseBody = smsAliyunAssist.send(sendSmsParamAliyunModel);

            // 保存发送记录
//            SmsRecordModel.builder()
//                    .sendSmsResponseBody(sendSmsResponseBody)
//                    .smsTemplateConfig(smsTemplateVo)
//                    .phone(verifyCode.getPhone())
//                    .content(JacksonUtil.toJson(sendSmsParamAliyunModel.getTemplateParam())).build()
//            smsSendService.saveSendLog();

            return SendVerifyCodeVo.builder()
                    .code(verifyCode.getVerifyContent())
//                    .msg(sendSmsResponseBody.getMessage())
                    .phone(verifyCode.getPhone())
//                    .verifyCode(smsProperties.isShow() ? verifyCode.getVerifyContent() : "******")
                    .serialNo(verifyCode.getSerialNo())
                    .build();
        } catch (Exception e) {
            // 发送失败时更新状态或删除记录
            log.error("发送短信验证码失败，手机号: {}, 序列号: {}", verifyCode.getPhone(), verifyCode.getSerialNo(), e);
            // 发送日志标记为失败
            throw SmsErrorCode.SMS_10002.buildException();
        }
    }

    /**
     * 校验短信验证码
     *
     * @param checkVerifyCodeModel 短信验证码校验入参
     * @author LXZ 2025/12/23 16:08
     */
    @Override
    public Boolean checkVerifyCode(CheckVerifyCodeModel checkVerifyCodeModel) {
        SmsVerifyCode smsVerifyCode = smsVerifyCodeRepository.getSmsVerifyCode(checkVerifyCodeModel.getPhone(),checkVerifyCodeModel.getVerifyContent(),
                checkVerifyCodeModel.getSerialNo(), checkVerifyCodeModel.getType().name());
        if (ObjectUtil.isEmpty(smsVerifyCode)) {
            throw  SmsErrorCode.SMS_10003.buildException();
        }
        if (AppConstants.Y.equals(smsVerifyCode.getStatus())){
            throw  SmsErrorCode.SMS_10004.buildException();
        }
        if (LocalDateTime.now().isAfter(smsVerifyCode.getExpiredAt())) {
            smsVerifyCode.setStatus("E");
            smsVerifyCode.setUpdateAt(LocalDateTime.now());
            smsVerifyCodeRepository.save(smsVerifyCode);
            throw  SmsErrorCode.SMS_10005.buildException();
        }
        smsVerifyCode.setStatus(AppConstants.Y);
        smsVerifyCode.setUpdateAt(LocalDateTime.now());
        smsVerifyCodeRepository.save(smsVerifyCode);
        return true;
    }

    /**
     * 验证签名
     * @param verifyCodeModel 参数
     * @param request 请求
     */
    private void verifySign(VerifyCodeModel verifyCodeModel , HttpServletRequest request ){
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        String tokenKey  = RedisUtil.genKey(SmsCacheEnum.SMS_TOKEN, verifyCodeModel.getToken());
        // 1. 校验 token
        Map<String , String> tokenData = RedisUtil.get(tokenKey, new TypeReference<Map<String, String>>() {});
        if (tokenData == null || tokenData.isEmpty()) {
            throw SmsErrorCode.SMS_TOKEN_EXPIRED.buildException();
        }

        if (!verifyCodeModel.getPhone().equals(tokenData.get("phone")) ||
                !ip.equals(tokenData.get("ip")) ||
                !ua.equals(tokenData.get("ua"))) {
            throw SmsErrorCode.SMS_TOKEN_INVALID.buildException();
        }

        // token 立即失效（一次性）
        RedisUtil.delete(tokenKey);
        // 2. 校验 timestamp（60 秒）
        long now = System.currentTimeMillis();
        if (Math.abs(now - verifyCodeModel.getTimestamp()) > 60_000) {
            throw SmsErrorCode.SMS_TOKEN_EXPIRED.buildException();
        }

        // 3. 校验 nonce（防重放）
        String nonceKey = RedisUtil.genKey(SmsCacheEnum.SMS_NONCE, verifyCodeModel.getNonce());
        if (Boolean.TRUE.equals(RedisUtil.hasKey(nonceKey))) {
            throw SmsErrorCode.SMS_REPEAT_REQUEST.buildException();
        }
        RedisUtil.set(nonceKey , "1", 60 , TimeUnit.SECONDS);

        // 4. 校验签名
        String serverSign = SignUtil.singHmacSHA256(
                verifyCodeModel.getPhone()+verifyCodeModel.getTimestamp()+verifyCodeModel.getNonce(),
                smsProperties.getSecretKey()
        );

        if (!serverSign.equals(verifyCodeModel.getSign())) {
            throw SmsErrorCode.SMS_SIGN_ERROR.buildException();
        }
    }
}
