package com.yuansaas.user.common.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yuansaas.common.enums.UserTypeEnum;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.redis.RedisUtil;
import com.yuansaas.user.client.entity.ClientUser;
import com.yuansaas.user.client.service.ClientUserService;
import com.yuansaas.user.common.enums.UserStatus;
import com.yuansaas.user.config.ServiceManager;
import com.yuansaas.user.system.entity.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 用户状态缓存
 *
 * @author HTB 2025/8/11 14:34
 */
@Component
@RequiredArgsConstructor
public class UserStatusCache {

//    private final SysUserService sysUserService;
    private final ClientUserService clientUserService;

    /**
     * 检查用户是否活跃（未锁定且启用）
     *
     * @param userId 用户ID
     * @param userType 用户类型
     * @return 用户是否活跃
     */
    public boolean isUserActive(Long userId, UserTypeEnum userType) {
        return  RedisUtil.getOrLoad(RedisUtil.genKey("userStatus", userType.name(), userId),
                new TypeReference<Boolean>() {},
                () -> loadUserStatus(userId, userType),
                60 * 60 * 24,
                TimeUnit.SECONDS);
//         loadUserStatus(userId, userType);
    }

    /**
     * 清除用户状态缓存
     *
     * @param userId 用户ID
     * @param userType 用户类型
     */
    public void evictUser(Long userId, UserTypeEnum userType) {
        // 缓存清除操作由注解自动处理
        RedisUtil.delete(RedisUtil.genKey("userStatus", userType.name() , userId));
    }

    /**
     * 实际加载用户状态
     */
    private boolean loadUserStatus(Long userId, UserTypeEnum userType) {
        if (userType == UserTypeEnum.YUAN_SHI_USER) {
            // 元识管理用户
            SysUser user = ServiceManager.sysUserService.findById(userId)
                    .orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在") );
            return UserStatus.active.matches(user.getStatus()) ;
        } if(userType == UserTypeEnum.MERCHANT_USER) {
            // todo 商户用户
            return false;
        }if(userType == UserTypeEnum.CLIENT_USER) {
            // 客户端用户
            ClientUser user = clientUserService.findById(userId)
                    .orElseThrow(() ->DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在"));
            return UserStatus.active.matches(user.getStatus()) ;
        }
        return false;
    }

}
