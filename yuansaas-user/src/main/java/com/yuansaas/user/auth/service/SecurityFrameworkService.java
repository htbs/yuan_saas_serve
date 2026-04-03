package com.yuansaas.user.auth.service;

/**
 * 定义权限相关的校验操作
 *
 * @author LXZ 2026/1/27  18:17
 */
public interface SecurityFrameworkService {

    /**
     * 判断是否有权限
     *
     * @param permission 权限
     * @return 是否
     */
    boolean hasPermission(String permission);

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param permissions 权限
     * @return 是否
     */
    boolean hasAnyPermissions(String... permissions);

    /**
     * 判断是否有角色
     *
     * @param role 角色
     * @return 是否
     */
    boolean hasRole(Long role);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles 角色数组
     * @return 是否
     */
    boolean hasAnyRoles(Long... roles);

//    /**
//     * 判断是否有授权
//     *
//     * @param scope 授权
//     * @return 是否
//     */
//    boolean hasScope(String scope);
//
//    /**
//     * 判断是否有授权范围，任一一个即可
//     *
//     * @param scope 授权范围数组
//     * @return 是否
//     */
//    boolean hasAnyScopes(String... scope);
}
