package com.yuansaas.user.auth.vo;

import com.yuansaas.common.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证响应
 *
 * @author HTB 2025/8/5 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthVo {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 用户类型
     */
    private UserTypeEnum userType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     *  用户名
     */
    private String username;

    /**
     *  头像
     */
    private String avatar;
}
