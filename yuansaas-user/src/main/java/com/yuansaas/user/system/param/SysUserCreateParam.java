package com.yuansaas.user.system.param;

import com.yuansaas.core.annotation.EnumValidate;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.integration.sms.enums.SendTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 创建系统用户参数
 *
 * @author HTB 2025/8/11 15:28
 */
@Data
public class SysUserCreateParam {

    /**
     * 商户编号
     */
    private String shopCode = AppContextUtil.getShopCode();
    /**
     * 用户名（账号）
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;
    /**
     * 头像
     */
    @NotNull(message = "头像不能为空")
    private String headUrl;

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
    /**
     * 序列号
     */
    @NotEmpty(message = "序列号不能为空！")
    private String serialNo;
    /**
     * 发送类型
     */
    @EnumValidate(enumClass = SendTypeEnum.class , message = "验证码类型必须为SendTypeEnum枚举里面的参数！")
    private SendTypeEnum sendSceneType;

    /**
     * 角色ids
     */
    @NotNull(message = "角色id不能为空")
    private List<Long> roleIds;
    /**
     * 手机验证码是否校验
     */
    private Boolean isPhoneVerifyCodeValid = true;
}
