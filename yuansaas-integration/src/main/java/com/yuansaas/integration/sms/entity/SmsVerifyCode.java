package com.yuansaas.integration.sms.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信校验entity
 *
 * @author LXZ 2025/12/23 15:44
 */
@Data
@Entity
@Table(name="sms_verify_code")
public class SmsVerifyCode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * '手机号码'
     */
    private String phone;
    /**
     * '验证码内容'
     */
    private String verifyContent;
    /**
     * '类别 注册：REG 修改：MODIFY 登录：LOGIN'
     */
    private String type;
    /**
     * '序列号'
     */
    private String serialNo;
    /**
     * '状态 N：未校验 Y：已使用 E:验证时已过期'
     */
    private String status;
    /**
     * '过期时间'
     */
    private LocalDateTime expiredAt;

}
