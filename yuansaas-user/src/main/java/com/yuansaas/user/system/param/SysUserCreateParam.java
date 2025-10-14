package com.yuansaas.user.system.param;

import lombok.Data;

/**
 * 创建系统用户参数
 *
 * @author HTB 2025/8/11 15:28
 */
@Data
public class SysUserCreateParam {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

}
