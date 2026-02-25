package com.yuansaas.user.users.service;

import com.yuansaas.core.page.RPage;
import com.yuansaas.user.users.entity.SysUser;
import com.yuansaas.user.users.param.FindUserParam;
import com.yuansaas.user.users.param.SysUserCreateParam;
import com.yuansaas.user.users.param.UpdateUserPwdParam;
import com.yuansaas.user.users.param.UserUpdateParam;
import com.yuansaas.user.users.vo.SysUserListVo;
import com.yuansaas.user.users.vo.SysUserVo;

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
     * 通过id查询系统用户并关联的角色、部门信息
     *
     * @param id  id
     * @return 系统用户
     */
    SysUserVo findLinkDateById(Long id);

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
     * 修改密码
     * @param updateUserPwd 用户修改请求
     * @return 修改成功的用户信息
     */
    Boolean updateUserPwd(UpdateUserPwdParam updateUserPwd);

    /**
     * 重置密码
     * @param id 用户修改请求
     * @return 修改成功的用户信息
     */
    Boolean resetUserResetPwd(Long id);

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
     * 列表查询
     * @param findUserParam 查询参数
     * @return roleListVo
     */
    RPage<SysUserListVo> getByPage(FindUserParam findUserParam);
}
