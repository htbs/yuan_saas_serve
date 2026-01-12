package com.yuansaas.user.auth.security;

import com.yuansaas.common.enums.UserTypeEnum;
import com.yuansaas.core.exception.AppException;
import com.yuansaas.core.exception.ex.AuthErrorCode;
import com.yuansaas.user.auth.model.CustomUserDetails;
import com.yuansaas.user.auth.properties.JwtProperties;
import com.yuansaas.user.client.entity.ClientUser;
import com.yuansaas.user.client.service.ClientUserService;
import com.yuansaas.user.common.service.UserStatusCache;
import com.yuansaas.user.config.ServiceManager;
import com.yuansaas.user.system.entity.SysUser;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 操作类
 *
 * @author HTB 2025/8/5 16:32
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtManager {

    private final JwtProperties jwtProperties;

    private final ClientUserService clientUserService;

    private final TokenBlacklistManager tokenBlacklistManager;

    private final UserStatusCache userStatusCache;

    private final JwtTokenParser jwtTokenParser;


    /**
     * 生成 JWT 访问令牌
     *
     * @param userDetails 用户信息
     * @return JWT 访问令牌
     */
    public String generateAccessToken(CustomUserDetails userDetails) {
        return buildToken(userDetails, jwtProperties.getExpiration() ,"access");
    }

    /**
     * 生成刷新 JWT 访问令牌
     *
     * @param userDetails 用户信息
     * @return JWT 访问令牌
     */
    public String generateRefreshToken(CustomUserDetails userDetails) {
         return buildToken(userDetails, jwtProperties.getRefreshExpiration(), "refresh");
    }

    private String buildToken(CustomUserDetails userDetails, long expiration , String tokenType) {
        // 构建 JWT 字符串
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId",userDetails.getUserId())
                .claim("userBaseRole",userDetails.getUserBaseRole().name())
                .claim("userType",userDetails.getUserType().name())
                .claim("tokenType",tokenType)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 过期时间毫秒
                .signWith(  jwtTokenParser.getSignInKey() , SignatureAlgorithm.HS512) // 签名算法和密钥 jwtSecret
                .compact();
    }

    /**
     * 校验 JWT 访问令牌
     * @param token JWT 访问令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            // 基本验证
            if(!jwtTokenParser.isTokenValid( token)){
                return false;
            }
            // 检验令牌黑名单
        if(tokenBlacklistManager.isTokenBlacklisted( token)){
            return false;
        }
        // 提取用户信息
            CustomUserDetails userDetails = parseToken(token);

            // 检查用户黑名单
            if (tokenBlacklistManager.isUserBlacklisted(userDetails.getUserId(), userDetails.getUserType())) {
                return false;
            }
            // 检查用户状态
            return userStatusCache.isUserActive(userDetails.getUserId(), userDetails.getUserType());
        } catch (AppException e) {
           return false;
        }

    }

    /**
     * 解析 JWT 访问令牌
     * @param token JWT 访问令牌
     * @return 用户信息
     */
    public CustomUserDetails parseToken(String token) {
        Claims claims = jwtTokenParser.extractAllClaims(token);
        Long userId = claims.get("userId", Long.class);
        UserTypeEnum userType = UserTypeEnum.getByCode(claims.get("userType", String.class));
        String username = claims.getSubject();

        // 根据用户类型加载用户详情
        return loadUserDetails(userType, userId, username);
    }

    private CustomUserDetails loadUserDetails(UserTypeEnum userType, Long userId, String username) {
        if (userType == UserTypeEnum.YUAN_SHI_USER) {
            SysUser user = ServiceManager.sysUserService.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("系统用户不存在"));
            return new CustomUserDetails(user);
        } if (userType == UserTypeEnum.CLIENT_USER){
            ClientUser user = clientUserService.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("客户端用户不存在"));
            return new CustomUserDetails(user);
        } if (userType == UserTypeEnum.MERCHANT_USER){
            // TODO 商户用户
            return null;
        }else {
            throw AuthErrorCode.AUTH_USER_NOT_FOUND.buildException();
        }
    }

    /**
     * 校验 JWT 访问令牌是否过期
     * @param token JWT 访问令牌
     * @return 是否过期
     */
    private boolean isTokenExpired(String token) {
        return jwtTokenParser.extractExpiration(token).before(new Date());
    }

}
