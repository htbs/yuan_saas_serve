package com.yuansaas.integration.sms.repository;

import com.yuansaas.integration.sms.entity.SmsTemplateConfig;
import com.yuansaas.integration.sms.entity.SmsVerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author LXZ 2025/12/23 16:15
 */
public interface SmsTemplateConfigRepository extends JpaRepository<SmsTemplateConfig, Long> {


   SmsTemplateConfig findByTemplateCodeAndLockStatus(String templateCode, String lockStatus);
}
