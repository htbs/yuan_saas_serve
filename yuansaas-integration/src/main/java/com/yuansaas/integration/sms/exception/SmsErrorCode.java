package com.yuansaas.integration.sms.exception;

import com.yuansaas.core.exception.IErrorCodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据相关异常码
 *
 * @author HTB 2025/7/28 16:07
 */
@Getter
@RequiredArgsConstructor
public enum SmsErrorCode implements IErrorCodeEnum<SmsErrorCode> {

    // ======================== 数据相关错误 ========================
    SMS_10001("SMS_0001" ,"五分钟内只能发送一次,请稍候再试"),
    SMS_10002("SMS_0002" ,"短信发送失败,请重试"),
    SMS_10003("SMS_0003" ,"验证码错误"),
    SMS_10004("SMS_0004" ,"验证码已失效"),
    SMS_10005("SMS_0005" ,"验证码已过期"),
    SMS_10006("SMS_0006" ,"发送短信开关关闭"),
    SMS_10007("SMS_0007" ,"验证码位数不合规，请核对！"),
    SMS_REQUEST_EXCEPTION("SMS_0008","调用第三方发送短信异常"),
    SMS_SERVER_EXCEPTION("SMS_0009" , "发送失败,短信服务不可用"),
    SMS_TEMPLATE_PARAMETER_VALIDATION_FAILED("SMS_0010" , "模板参数验证失败"),
    SMS_TEMPLATE_NOT_FOUND("SMS_0011" , "模板不存在"),

    ;

    private final String  code;

    private final String message;
}
