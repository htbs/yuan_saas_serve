package com.yuansaas.user.system.service;

import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.menu.vo.MenuListVo;
import com.yuansaas.user.system.entity.SysUser;
import com.yuansaas.user.system.param.SysUserCreateParam;
import com.yuansaas.user.system.param.UserUpdateParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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
     * 创建用户
     * @param sysUserCreateParam 用户创建请求
     * @return 创建成功的用户信息
     */
    SysUser saveUser(SysUserCreateParam sysUserCreateParam);
    /**
     * 更新用户
     * @param userUpdateParam 用户更新请求
     * @return 更新成功的用户信息 true/false
     */
    Boolean updateUser(UserUpdateParam userUpdateParam);
    /**
     * 锁定用户
     * @param userId  用户id
     */
    Boolean lockUser(Long userId);

    /**
     * 解锁用户
     * @param userId  用户id
     */
    Boolean unlockUser(Long userId);


    /**
     * 删除用户
     * @param userId  用户id
     */
    Boolean deleteUser(Long userId);
    /**
     * 根据用户id查询菜单列表
     * @param userId 用户id
     * @return 菜单列表
     */
    List<MenuListVo> findMenuListByUserId(Long userId);
}
