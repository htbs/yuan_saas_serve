package com.yuansaas.user.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.user.auth.service.SecurityFrameworkService;
import com.yuansaas.user.permission.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 *  默认的 {@link SecurityFrameworkService} 实现类
 *
 * @author LXZ 2026/1/27  18:17
 */
@Service
@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private final PermissionService permissionService;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        // 权限校验
        Long userId = AppContextUtil.requireUserId();
        if (userId == null) {
            return false;
        }
        return permissionService.hasAnyPermissions(userId, permissions);
    }

    @Override
    public boolean hasRole(Long role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(Long... roles) {
        // 权限校验
        Long userId = AppContextUtil.requireUserId();
        if (userId == null) {
            return false;
        }
        return permissionService.hasAnyRoles(userId, roles);
    }

//    @Override
//    public boolean hasScope(String scope) {
//        return hasAnyScopes(scope);
//    }
//
//    @Override
//    public boolean hasAnyScopes(String... scope) {
//
//        // 权限校验
//        LoginUser user = SecurityFrameworkUtils.getLoginUser();
//        if (user == null) {
//            return false;
//        }
//        return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
//    }

}
