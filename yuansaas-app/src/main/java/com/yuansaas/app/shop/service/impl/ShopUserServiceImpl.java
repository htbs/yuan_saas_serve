package com.yuansaas.app.shop.service.impl;

import com.yuansaas.app.shop.param.ShopUserSaveParam;
import com.yuansaas.app.shop.service.ShopUserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 商铺用户 Service 实现类
 *
 * @author LXZ 2026/1/28 19:41
 */
@Service
public class ShopUserServiceImpl implements ShopUserService {
    /**
     * 创建用户
     *
     * @param createReqVO 用户信息
     * @return 用户编号
     */
    @Override
    public Long createUser(@Valid ShopUserSaveParam createReqVO) {
        return 0L;
    }

    /**
     * 修改用户
     *
     * @param updateReqVO 用户信息
     */
    @Override
    public void updateUser(UserSaveReqVO updateReqVO) {

    }

    /**
     * 更新用户的最后登陆信息
     *
     * @param id      用户编号
     * @param loginIp 登陆 IP
     */
    @Override
    public void updateUserLogin(Long id, String loginIp) {

    }

    /**
     * 修改用户个人信息
     *
     * @param id    用户编号
     * @param reqVO 用户个人信息
     */
    @Override
    public void updateUserProfile(Long id, UserProfileUpdateReqVO reqVO) {

    }

    /**
     * 修改用户个人密码
     *
     * @param id    用户编号
     * @param reqVO 更新用户个人密码
     */
    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {

    }

    /**
     * 修改密码
     *
     * @param id       用户编号
     * @param password 密码
     */
    @Override
    public void updateUserPassword(Long id, String password) {

    }

    /**
     * 修改状态
     *
     * @param id     用户编号
     * @param status 状态
     */
    @Override
    public void updateUserStatus(Long id, Integer status) {

    }

    /**
     * 删除用户
     *
     * @param id 用户编号
     */
    @Override
    public void deleteUser(Long id) {

    }

    /**
     * 批量删除用户
     *
     * @param ids 用户编号数组
     */
    @Override
    public void deleteUserList(List<Long> ids) {

    }

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    @Override
    public AdminUserDO getUserByUsername(String username) {
        return null;
    }

    /**
     * 通过手机号获取用户
     *
     * @param mobile 手机号
     * @return 用户对象信息
     */
    @Override
    public AdminUserDO getUserByMobile(String mobile) {
        return null;
    }
}
