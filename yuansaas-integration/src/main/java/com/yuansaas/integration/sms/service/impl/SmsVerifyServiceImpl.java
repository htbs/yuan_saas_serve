package com.yuansaas.integration.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.jackson.JacksonUtil;
import com.yuansaas.core.utils.id.SnowflakeIdGenerator;
import com.yuansaas.integration.sms.assist.SmsAliYunAssist;
import com.yuansaas.integration.sms.entity.SmsVerifyCode;
import com.yuansaas.integration.sms.exception.SmsErrorCode;
import com.yuansaas.integration.sms.model.CheckVerifyCodeModel;
import com.yuansaas.integration.sms.model.SendSmsParamAliyunModel;
import com.yuansaas.integration.sms.model.SmsRecordModel;
import com.yuansaas.integration.sms.model.VerifyCodeModel;
import com.yuansaas.integration.sms.properties.SmsProperties;
import com.yuansaas.integration.sms.repository.SmsVerifyCodeRepository;
import com.yuansaas.integration.sms.service.SmsSendService;
import com.yuansaas.integration.sms.service.SmsTemplateConfigService;
import com.yuansaas.integration.sms.service.SmsVerifyService;
import com.yuansaas.integration.sms.vo.SendVerifyCodeVo;
import com.yuansaas.integration.sms.vo.SmsTemplateVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 发送验证码实现
 * @author LXZ 2025/12/23 16:08
 */
@Service
@RequiredArgsConstructor
public class SmsVerifyServiceImpl implements SmsVerifyService {

    private final SmsVerifyCodeRepository smsVerifyCodeRepository;
    private final SmsProperties smsProperties;
    private final SmsAliYunAssist smsAliyunAssist;
    private final SmsSendService smsSendService;
    private final SmsTemplateConfigService smsTemplateConfigService;
    private final SnowflakeIdGenerator idWorker;

    /**
     * 获取短信验证码
     *
     * @param verifyCodeModel 验证码模型
     * @author LXZ 2025/12/23 16:08
     */
    @Override
    @Transactional
    public SendVerifyCodeVo getVerifyCode(VerifyCodeModel verifyCodeModel) {
        // 验证模版是否存在
        SmsTemplateVo bySmsTemplateCode = smsTemplateConfigService.findBySmsTemplateCode(verifyCodeModel.getSmsAliYunTemplateCode());
        // 5分钟内不能重复获取验证码
        SmsVerifyCode smsVerifyCode = smsVerifyCodeRepository.getSmsVerifyCodeByPhoneAndTypes(verifyCodeModel.getPhone(), verifyCodeModel.getTypes().name());
        if (ObjectUtil.isNotEmpty(smsVerifyCode)) {
            if(LocalDateTime.now().isBefore(smsVerifyCode.getCreateAt().plusSeconds(smsProperties.getSmsSendIntervalMin()))){
              throw  SmsErrorCode.SMS_10001.buildException( smsProperties.getSmsSendIntervalMin() / AppConstants.DEFAULT_KEEP_ALIVE_SECONDS + "分钟内只能发送一次,请稍候再试");
            }
        }
        // 生成验证码
        SmsVerifyCode verifyCode = new SmsVerifyCode();
        verifyCode.setPhone(verifyCodeModel.getPhone());
        verifyCode.setType(verifyCodeModel.getTypes().name());
        verifyCode.setVerifyContent(RandomUtil.randomNumbers(smsProperties.getCodeLength()));
        verifyCode.setSerialNo(String.valueOf(idWorker.nextId()));
        verifyCode.setExpiredAt(LocalDateTime.now().plusSeconds(smsProperties.getCodeExpireTime()));
        verifyCode.setUpdateAt(LocalDateTime.now());
        verifyCode.setCreateAt(LocalDateTime.now());
        verifyCode.setStatus(AppConstants.N);
        // 保存验证码
        smsVerifyCodeRepository.save(verifyCode);

        // 发送验证码
        Map<String , Object> param = new HashMap<>();
        param.put("k1" , verifyCode.getVerifyContent());
        SendSmsParamAliyunModel sendSmsParamAliyunModel =  new SendSmsParamAliyunModel()
                .setTemplateParam(param)
                .setTemplateCode(verifyCodeModel.getSmsAliYunTemplateCode())
                .setSignName("医链健康")
                .setPhoneNumbers(verifyCode.getPhone());
        SendSmsResponseBody sendSmsResponseBody = smsAliyunAssist.send(sendSmsParamAliyunModel);

        // 保存发送记录
        smsSendService.saveSendLog(SmsRecordModel.builder()
                        .sendSmsResponseBody(sendSmsResponseBody)
                        .smsTemplateConfig(bySmsTemplateCode)
                        .phone(verifyCode.getPhone())
                        .content(JacksonUtil.toJson(sendSmsParamAliyunModel.getTemplateParam())).build());

        return SendVerifyCodeVo.builder()
                .code(sendSmsResponseBody.getCode())
                .msg(sendSmsResponseBody.getMessage())
                .phone(verifyCode.getPhone())
                .verifyCode(smsProperties.isShow() ? verifyCode.getVerifyContent() : "******")
                .serialNo(verifyCode.getSerialNo())
                .build();
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
}
