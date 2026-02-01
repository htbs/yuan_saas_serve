package com.yuansaas.app.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.app.shop.entity.ShopUser;
import com.yuansaas.app.shop.param.ShopUserSaveParam;
import com.yuansaas.app.shop.param.ShopUserUpdateParam;
import com.yuansaas.app.shop.repository.ShopUserRepository;
import com.yuansaas.app.shop.service.ShopUserService;
import com.yuansaas.app.shop.service.mapstruct.ShopMapStruct;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.exception.ex.ParamErrorCode;
import com.yuansaas.user.permission.params.AssignUserDeptParam;
import com.yuansaas.user.permission.params.AssignUserRoleParam;
import com.yuansaas.user.permission.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 商铺用户 Service 实现类
 *
 * @author LXZ 2026/1/28 19:41
 */
@Service
@RequiredArgsConstructor
public class ShopUserServiceImpl implements ShopUserService {

    private final ShopMapStruct shopMapStruct;
    private final ShopUserRepository shopUserRepository;
    private final PermissionService permissionService;

    /**
     * 创建用户
     *
     * @param shopUserSaveParam 用户信息
     * @return 用户编号
     */
    @Override
    public Long createUser(@Valid ShopUserSaveParam shopUserSaveParam) {
        if (ObjectUtil.isEmpty(shopUserSaveParam.getShopCode())) {
            throw ParamErrorCode.PARAMETER_REQUIRED.buildException("商铺code不能为空");
        }
        // 组装用户数据 并保存
        ShopUser shopUserSave = shopMapStruct.toShopUserSave(shopUserSaveParam);
        // 获取用户的岗位编号
        shopUserSave.setPostCode(getPostCode(shopUserSaveParam.getShopCode()));
        shopUserSave.init();
        shopUserRepository.save(shopUserSave);

        // 给用户授权角色
        // 授权角色权限
        permissionService.assignUserRole(AssignUserRoleParam.builder().userId(shopUserSave.getId()).roleId(shopUserSaveParam.getRoleId()).build());
        // 授权部门权限
        permissionService.assignUserDept(AssignUserDeptParam.builder().shopCode(shopUserSaveParam.getShopCode()).userId(shopUserSave.getId()).build());
        return 0L;
    }

    /**
     * 修改用户
     *
     * @param userUpdateParam 用户信息
     */
    @Override
    public void updateUser(@Valid ShopUserUpdateParam userUpdateParam) {
        shopUserRepository.findById(userUpdateParam.getId()).ifPresentOrElse( shopUser ->{
            shopUser.setSex(userUpdateParam.getSex());
            shopUser.setPhone(userUpdateParam.getPhone());
            shopUser.setHeadUrl(userUpdateParam.getAvatar());
            shopUser.setEmail(userUpdateParam.getEmail());
            if (ObjectUtil.isNotEmpty(userUpdateParam.getRoleId())) {
                permissionService.assignUserRole(AssignUserRoleParam.builder().userId(shopUser.getId()).roleId(userUpdateParam.getRoleId()).build());
            }
            if (ObjectUtil.isNotEmpty(userUpdateParam.getDeptId())) {
                permissionService.assignUserDept(AssignUserDeptParam.builder().shopCode(shopUser.getShopCode()).userId(shopUser.getId()).deptId(userUpdateParam.getDeptId()).build());
            }
            shopUser.update();
            shopUserRepository.save(shopUser);
        },()->{
          throw DataErrorCode.DATA_NOT_FOUND.buildException();
        }
        );
    }

//    /**
//     * 更新用户的最后登陆信息
//     *
//     * @param id      用户编号
//     * @param loginIp 登陆 IP
//     */
//    @Override
//    public void updateUserLogin(Long id, String loginIp) {
//
//    }
//
//    /**
//     * 修改用户个人信息
//     *
//     * @param id    用户编号
//     * @param reqVO 用户个人信息
//     */
//    @Override
//    public void updateUserProfile(Long id, UserProfileUpdateReqVO reqVO) {
//
//    }
//
//    /**
//     * 修改用户个人密码
//     *
//     * @param id    用户编号
//     * @param reqVO 更新用户个人密码
//     */
//    @Override
//    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
//
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param id       用户编号
//     * @param password 密码
//     */
//    @Override
//    public void updateUserPassword(Long id, String password) {
//
//    }
//
//    /**
//     * 修改状态
//     *
//     * @param id     用户编号
//     * @param status 状态
//     */
//    @Override
//    public void updateUserStatus(Long id, Integer status) {
//
//    }
//
//    /**
//     * 删除用户
//     *
//     * @param id 用户编号
//     */
//    @Override
//    public void deleteUser(Long id) {
//
//    }
//
//    /**
//     * 批量删除用户
//     *
//     * @param ids 用户编号数组
//     */
//    @Override
//    public void deleteUserList(List<Long> ids) {
//
//    }
//
//    /**
//     * 通过用户名查询用户
//     *
//     * @param username 用户名
//     * @return 用户对象信息
//     */
//    @Override
//    public AdminUserDO getUserByUsername(String username) {
//        return null;
//    }
//
//    /**
//     * 通过手机号获取用户
//     *
//     * @param mobile 手机号
//     * @return 用户对象信息
//     */
//    @Override
//    public AdminUserDO getUserByMobile(String mobile) {
//        return null;
//    }



    /**
     * 获取岗位编号
     */
    private String getPostCode(String shopCode){
        String latestPostCode = shopUserRepository.findLatestPostCodeByShopCode(shopCode);
        if (ObjectUtil.isEmpty(latestPostCode)) {
            return String.format("%03d" , AppConstants.ONE);
        } else {
            int code =  Integer.parseInt(latestPostCode) + 1;
            if (code > AppConstants.NINE_HUNDRED_NINETY_NINE ) {
                return Integer.toString(code);
            }
            return String.format("%03d" , code);
        }

    }
}
