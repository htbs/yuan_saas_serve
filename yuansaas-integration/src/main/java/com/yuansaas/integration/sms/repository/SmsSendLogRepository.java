package com.yuansaas.integration.sms.repository;

import com.yuansaas.integration.sms.entity.SmsSendLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author LXZ 2025/12/23 16:15
 */
public interface SmsSendLogRepository extends JpaRepository<SmsSendLog, Long> {
}
