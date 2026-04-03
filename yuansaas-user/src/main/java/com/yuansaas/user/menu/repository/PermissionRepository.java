package com.yuansaas.user.menu.repository;

import com.yuansaas.user.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * 权限配置表 数据库操作管理
 *
 * @author LXZ 2026/1/27 12:18
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    /**
     * 通过权限点code查询权限信息
     *
     * @param permissionCode 权限code
     */
    List<Permission> findByPermissionCodeIn(List<String> permissionCode);
}
