package com.yuansaas.user.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.user.menu.service.MenuService;
import com.yuansaas.user.permission.entity.Permission;
import com.yuansaas.user.menu.repository.PermissionRepository;
import com.yuansaas.user.permission.params.AssignUserRoleParam;
import com.yuansaas.user.permission.params.AuthorizeMenuParam;
import com.yuansaas.user.permission.service.PermissionService;
import com.yuansaas.user.permission.service.RoleMenuService;
import com.yuansaas.user.permission.service.RoleUserService;
import com.yuansaas.user.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * 权限点管理实现类
 *
 * @author LXZ 2026/1/27 12:17
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleMenuService roleMenuService;
    private final RoleUserService roleUserService;
    private final MenuService menuService;
    private final RoleService roleService;

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param userId      用户编号
     * @param permissions 权限
     * @return 是否
     */
    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        List<Long> roles = roleUserService.getRoleIdList(userId);
        if (CollUtil.isEmpty(roles)) {
            return false;
        }

        // 遍历判断每个权限，如果有一满足，说明有权限
        for (String permission : permissions) {
            if (hasAnyPermission(roles, permission)) {
                return true;
            }
        }
        // 如果是超管，也说明有权限
        return roleService.hasAnySuperAdmin(roles);
    }

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param userId
     * @param roles  角色数组
     * @return 是否
     */
    @Override
    public boolean hasAnyRoles(Long userId, Long... roles) {
        return false;
    }

    /**
     * 通过权限点code查询权限信息
     *
     * @param permissionCode 权限code
     */
    @Override
    public List<Permission> getByPermissionCode(List<String> permissionCode) {
        return permissionRepository.findByPermissionCodeIn(permissionCode);
    }

    /**
     * 通过权限点code查询权限信息 为空则抛出异常
     *
     * @param permissionCode 权限code
     */
    @Override
    public List<Permission> getByPermissionCodeOrThrow(List<String> permissionCode) {
        List<Permission> byPermissionCodeIn = permissionRepository.findByPermissionCodeIn(permissionCode);
        if (ObjectUtil.isEmpty(byPermissionCodeIn)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        }
        return byPermissionCodeIn;
    }

    /**
     * 授权角色菜单
     *
     * @param authorizeMenuParam 授权参数
     * @return true/false
     */
    @Override
    public Boolean assignRoleMenu(AuthorizeMenuParam authorizeMenuParam) {
        roleMenuService.assignRoleMenu(authorizeMenuParam.getRoleId(), authorizeMenuParam.getMenuIds());
        return true;
    }

    /**
     * 获取角色拥有的菜单code
     *
     * @param roleId 角色ID
     * @return Long
     */
    @Override
    public List<Long> getRoleMenuListByRoleId(Long roleId) {
        return roleMenuService.getMenuIdList(roleId);
    }

    /**
     * 获取菜单拥有的角色id
     *
     * @param menuId 菜单ID
     * @return Long
     */
    @Override
    public List<Long> getRoleIdsByMenuId(Long menuId) {
        return roleMenuService.getRoleIdListByMenuIds(menuId);
    }

    /**
     * 分配角色给用户
     *
     * @param assignUserRoleParam 分配参数
     * @return true/false
     */
    @Override
    public Boolean assignUserRole(AssignUserRoleParam assignUserRoleParam) {
        roleUserService.assignUserRole(assignUserRoleParam.getUserId() , assignUserRoleParam.getRoleId());
        return true;
    }

    /**
     * 获取用户拥有的角色code
     *
     * @param userId 用户id
     * @return Long
     */
    @Override
    public List<Long> getUserRoleListByRoleId(Long userId) {
        return roleUserService.getRoleIdList(userId);
    }

    /**
     * 判断指定角色，是否拥有该 permission 权限
     *
     * @param roles 指定角色数组
     * @param permission 权限标识
     * @return 是否拥有
     */
    private boolean hasAnyPermission(List<Long> roles, String permission) {
        List<Long> menuButtonIds = menuService.findByPermission(permission);
        if (ObjectUtil.isEmpty(menuButtonIds)) {
            return false;
        }
        // 判断是否有权限
        for (Long menuButtonId : menuButtonIds) {
            // 获得拥有该菜单的角色编号集合
            List<Long> menuRoleIds = roleMenuService.getRoleIdListByMenuIds(menuButtonId);
            // 如果有交集，说明有权限
            if (CollUtil.containsAny(menuRoleIds, roles)) {
                return true;
            }
        }
        return false;
    }

}
