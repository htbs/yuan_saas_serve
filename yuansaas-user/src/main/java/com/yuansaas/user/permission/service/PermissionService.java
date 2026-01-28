package com.yuansaas.user.permission.service;

import com.yuansaas.user.permission.entity.Permission;
import com.yuansaas.user.permission.params.AssignUserRoleParam;
import com.yuansaas.user.permission.params.AuthorizeMenuParam;

import java.util.List;

/**
 *
 * 权限点管理service
 *
 * @author LXZ 2026/1/27 12:14
 */
public interface PermissionService {

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param userId      用户编号
     * @param permissions 权限
     * @return 是否
     */
    boolean hasAnyPermissions(Long userId, String... permissions);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles 角色数组
     * @return 是否
     */
    boolean hasAnyRoles(Long userId, Long... roles);


    // ======================================权限点查询===============================================

    /**
     * 通过权限点code查询权限信息
     * @param permissionCode  权限code
     */
    List<Permission> getByPermissionCode(List<String> permissionCode);

    /**
     * 通过权限点code查询权限信息 为空则抛出异常
     * @param permissionCode  权限code
     */
    List<Permission> getByPermissionCodeOrThrow(List<String> permissionCode);


    // ======================================角色分配权限===============================================

    /**
     * 授权角色菜单
     * @param authorizeMenuParam 授权参数
     * @return true/false
     */
    Boolean assignRoleMenu(AuthorizeMenuParam authorizeMenuParam);
    /**
     * 获取角色拥有的菜单id
     * @param roleId 角色ID
     * @return Long
     */
    List<Long> getRoleMenuListByRoleId(Long roleId);

    /**
     * 获取菜单拥有的角色id
     * @param menuId 菜单ID
     * @return Long
     */
    List<Long> getRoleIdsByMenuId(Long menuId);


    /**
     * 分配角色给用户
     * @param assignUserRoleParam 分配参数
     * @return true/false
     */
    Boolean assignUserRole(AssignUserRoleParam assignUserRoleParam);
    /**
     * 获取用户拥有的角色code
     * @param  userId 用户id
     * @return Long
     */
    List<Long> getUserRoleListByRoleId(Long userId);

}
