package com.yuansaas.user.users.service;


import com.yuansaas.user.users.entity.ShopUser;
import com.yuansaas.user.users.param.ShopUserSaveParam;
import com.yuansaas.user.users.param.ShopUserUpdateParam;
import com.yuansaas.user.users.param.UpdateUserPwdParam;
import jakarta.validation.Valid;

import java.util.Optional;


/**
 *
 * 商铺用户 Service 接口
 *
 * @author LXZ 2026/1/28 19:36
 */
public interface ShopUserService {

    /**
     * 创建用户
     *
     * @param shopUserSaveParam 用户信息
     * @return 用户编号
     */
    Boolean createUser(@Valid ShopUserSaveParam shopUserSaveParam);


    /**
     * 修改用户
     *
     * @param userUpdateParam 用户信息
     */
    void updateUser(@Valid ShopUserUpdateParam userUpdateParam);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
     Optional<ShopUser> getUserByUsername(String username);

    /**
     * 通过用户名查询用户
     *
     * @param id 用户id
     * @return 用户对象信息
     */
    Optional<ShopUser> getUserById(Long id);


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
     * 冻结用户
     * @param id 用户id
     * @return 冻结成功的用户信息 true or false
     */
    Boolean lockUser(Long id);

    /**
     * 解锁用户
     * @param id 用户id
     * @return 解释成功的用户信息 true or false
     */
    Boolean unlockUser(Long id);

    /**
     * 删除用户
     * @param id 用户id
     * @return  删除成功的用户信息 true or false
     */
    Boolean deleteUser(Long id);
}
