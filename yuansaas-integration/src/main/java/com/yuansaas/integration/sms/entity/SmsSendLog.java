package com.yuansaas.integration.sms.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import com.yuansaas.integration.sms.enums.SmsSceneEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信发送记录entity
 *
 * @author rlf 2023/10/24 15:26
 */
@Data
@Entity
@Table(name = "sms_send_log")
public class SmsSendLog extends BaseEntity implements Serializable {

    /**
     * 短信类别（verify_code：验证码｜sms_notify：短信通知）
     */
    private SmsSceneEnum smsType;
    /**
     * 短信内容
     */
    private String  content;
    /**
     *c短信模板字典表KEY值
     */
    private String  templateKey;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 会话ID
     */
    private String requestId;
    /**
     * 短信发送响应code码
     */
    private String respCode;
    /**
     * 短信发送响应错误信息
     */
    private String errorMsg;

    /**
     * 短信发送状态（success：成功｜fail：失败）
     */
    private String status;

    /**
     * 标记发送失败
     *
     * @param errorCode 响应错误码
     * @param errorMessage 错误消息
     */
    public void markAsFailed(String errorCode, String errorMessage) {
        this.status = "N";
        this.respCode = errorCode;
        this.errorMsg = errorMessage;
        super.init();
    }

    /**
     * 标记发送成功
     */
    public void markAsSuccess() {
        this.status = "Y";
        super.init();
    }
}
