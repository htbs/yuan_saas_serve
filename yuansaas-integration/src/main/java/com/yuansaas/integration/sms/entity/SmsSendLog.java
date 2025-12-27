package com.yuansaas.integration.sms.entity;

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
public class SmsSendLog implements Serializable {


    /**
     * 主键ID
     */
    @Id
    @GenericGenerator(
            name = "id",
            strategy = "com.yuansaas.core.jpa.id.CustomIdentityGenerator"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "id"
    )
    private Long id;

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
     * 创建时间
     */
    private LocalDateTime createAt;
    /**
     * 更新时间
     */
    private LocalDateTime updateAt;
    /**
     * 备注
     */
    private String remark;
}
