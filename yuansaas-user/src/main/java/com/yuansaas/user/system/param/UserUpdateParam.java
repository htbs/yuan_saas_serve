package com.yuansaas.user.system.param;

import com.yuansaas.integration.sms.enums.SendTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 创建系统用户参数
 *
 * @author lxz 2025/12/21 17:51
 */
@Data
public class UserUpdateParam {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;
    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
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
    private String phone;
    /**
     * 验证码
     */
    private String verifyCode;
    /**
     * 序列号
     */
    private String serialNo;
    /**
     * 发送类型
     */
    private SendTypeEnum sendSceneType;

    /**
     * 角色类型
     */
    @NotNull(message = "角色类型不能为空")
    private List<Long> roleIds;


}
