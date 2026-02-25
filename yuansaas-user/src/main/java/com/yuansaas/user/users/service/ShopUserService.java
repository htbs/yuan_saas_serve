package com.yuansaas.user.users.service;


import com.yuansaas.user.users.entity.ShopUser;
import com.yuansaas.user.users.param.ShopUserSaveParam;
import com.yuansaas.user.users.param.ShopUserUpdateParam;
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

//    /**
//     * 更新用户的最后登陆信息
//     *
//     * @param id 用户编号
//     * @param loginIp 登陆 IP
//     */
//    void updateUserLogin(Long id, String loginIp);
//
//    /**
//     * 修改用户个人信息
//     *
//     * @param id 用户编号
//     * @param reqVO 用户个人信息
//     */
//    void updateUserProfile(Long id, @Valid UserProfileUpdateReqVO reqVO);
//
//    /**
//     * 修改用户个人密码
//     *
//     * @param id 用户编号
//     * @param reqVO 更新用户个人密码
//     */
//    void updateUserPassword(Long id, @Valid UserProfileUpdatePasswordReqVO reqVO);
//
//    /**
//     * 修改密码
//     *
//     * @param id       用户编号
//     * @param password 密码
//     */
//    void updateUserPassword(Long id, String password);
//
//    /**
//     * 修改状态
//     *
//     * @param id     用户编号
//     * @param status 状态
//     */
//    void updateUserStatus(Long id, Integer status);
//
//    /**
//     * 删除用户
//     *
//     * @param id 用户编号
//     */
//    void deleteUser(Long id);
//
//    /**
//     * 批量删除用户
//     *
//     * @param ids 用户编号数组
//     */
//    void deleteUserList(List<Long> ids);
//
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
//
//    /**
//     * 通过手机号获取用户
//     *
//     * @param mobile 手机号
//     * @return 用户对象信息
//     */
//    AdminUserDO getUserByMobile(String mobile);
}
