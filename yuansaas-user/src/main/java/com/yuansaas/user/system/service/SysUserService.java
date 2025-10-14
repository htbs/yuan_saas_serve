package com.yuansaas.user.system.service;

import com.yuansaas.user.system.entity.SysUser;

import java.util.Optional;

/**
 * 系统用户
 *
 * @author HTB 2025/7/31 16:23
 */
public interface SysUserService {

    /**
     * 通过id查询系统用户
     *
     * @param id  id
     * @return 系统用户
     */
    Optional<SysUser> findById(Long id);

    /**
     * 通过用户名查询系统用户
     *
     * @param username  用户名
     * @return 系统用户
     */
    Optional<SysUser> findByUsername(String username);

    /**
     * 锁定用户
     * @param userId  用户id
     */
    void lockUser(Long userId);

    /**
     * 解锁用户
     * @param userId  用户id
     */
    void unlockUser(Long userId);


}
