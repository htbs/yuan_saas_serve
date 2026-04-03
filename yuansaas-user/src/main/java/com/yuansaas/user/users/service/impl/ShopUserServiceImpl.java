package com.yuansaas.user.users.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.AuthErrorCode;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.exception.ex.ParamErrorCode;
import com.yuansaas.user.common.enums.UserStatus;
import com.yuansaas.user.config.AppProperties;
import com.yuansaas.user.permission.params.AssignUserDeptParam;
import com.yuansaas.user.permission.params.AssignUserRoleParam;
import com.yuansaas.user.permission.service.PermissionService;
import com.yuansaas.user.users.entity.ShopUser;
import com.yuansaas.user.users.param.ShopUserSaveParam;
import com.yuansaas.user.users.param.ShopUserUpdateParam;
import com.yuansaas.user.users.param.UpdateUserPwdParam;
import com.yuansaas.user.users.repository.ShopUserRepository;
import com.yuansaas.user.users.service.ShopUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * 商铺用户 Service 实现类
 *
 * @author LXZ 2026/1/28 19:41
 */
@Service
@RequiredArgsConstructor
public class ShopUserServiceImpl implements ShopUserService {

    private final ShopUserRepository shopUserRepository;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    /**
     * 创建用户
     *
     * @param shopUserSaveParam 用户信息
     * @return 用户编号
     */
    @Override
    public Boolean createUser(@Valid ShopUserSaveParam shopUserSaveParam) {
        if (ObjectUtil.isEmpty(shopUserSaveParam.getShopCode())) {
            throw ParamErrorCode.PARAMETER_REQUIRED.buildException("商铺code不能为空");
        }
        // 组装用户数据 并保存
        ShopUser shopUserSave = toShopUserSave(shopUserSaveParam);
        // 获取用户的岗位编号
        shopUserSave.setPostCode(getPostCode(shopUserSaveParam.getShopCode()));
        shopUserSave.init();
        shopUserRepository.save(shopUserSave);

        // 给用户授权角色
        // 授权角色权限
        permissionService.assignUserRole(AssignUserRoleParam.builder().userId(shopUserSave.getId()).roleId(shopUserSaveParam.getRoleId()).build());
        // 授权部门权限
        permissionService.assignUserDept(AssignUserDeptParam.builder().shopCode(shopUserSaveParam.getShopCode()).userId(shopUserSave.getId()).build());
        return true;
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

    @Override
    public Optional<ShopUser> getUserByUsername(String username) {
        return shopUserRepository.findByUserName(username);
    }

    @Override
    public Optional<ShopUser> getUserById(Long id) {
        return shopUserRepository.findById(id);
    }

    /**
     * 修改密码
     *
     * @param updateUserPwd 用户修改请求
     * @return 修改成功的用户信息
     */
    @Override
    public Boolean updateUserPwd(UpdateUserPwdParam updateUserPwd) {
        shopUserRepository.findById(updateUserPwd.getUserId()).ifPresentOrElse(sysUser -> {
                    // 加密密码：passwordEncoder.encode(request.getPassword())
                    if (!passwordEncoder.matches(updateUserPwd.getOldPassword(), sysUser.getPassword())) {
                        throw AuthErrorCode.AUTHENTICATION_FAILED.buildException("旧密码输入错误，请重新输入") ;
                    }
                    sysUser.setPassword(passwordEncoder.encode(updateUserPwd.getNewPassword()));
                    sysUser.setUpdateAt(LocalDateTime.now());
                    sysUser.setUpdateBy(AppContextUtil.getUserInfo());
                    shopUserRepository.save(sysUser);
                }
                ,() ->{
                    throw  DataErrorCode.DATA_NOT_FOUND.buildException();
                }
        );
        return true;
    }

    /**
     * 重置密码
     *
     * @param id 用户修改请求
     * @return 修改成功的用户信息
     */
    @Override
    public Boolean resetUserResetPwd(Long id) {
        shopUserRepository.findById(id).ifPresentOrElse(sysUser -> {
                    sysUser.setPassword(passwordEncoder.encode(appProperties.getDefaultPassword()));
                    sysUser.setUpdateAt(LocalDateTime.now());
                    sysUser.setUpdateBy(AppContextUtil.getUserInfo());
                    shopUserRepository.save(sysUser);
                }
                ,() ->{
                    throw  DataErrorCode.DATA_NOT_FOUND.buildException();
                }
        );
        return true;
    }

    /**
     * 冻结用户
     *
     * @param id 用户id
     * @return 冻结成功的用户信息 true or false
     */
    @Override
    public Boolean lockUser(Long id) {
        shopUserRepository.findById(id).ifPresentOrElse(user -> {
            user.setStatus(UserStatus.suspended.name());
            user.setUpdateAt(LocalDateTime.now());
            user.setUpdateBy(AppContextUtil.getUserInfo());
            shopUserRepository.save(user);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return true;
    }

    /**
     * 解锁用户
     *
     * @param id 用户id
     * @return 解释成功的用户信息 true or false
     */
    @Override
    public Boolean unlockUser(Long id) {
        shopUserRepository.findById(id).ifPresentOrElse(user -> {
            user.setStatus(UserStatus.active.name());
            user.setUpdateAt(LocalDateTime.now());
            user.setUpdateBy(AppContextUtil.getUserInfo());
            shopUserRepository.save(user);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return true;
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除成功的用户信息 true or false
     */
    @Override
    public Boolean deleteUser(Long id) {
        shopUserRepository.findById(id).ifPresentOrElse(user -> {
            user.setStatus(UserStatus.deleted.name());
            user.setUpdateAt(LocalDateTime.now());
            user.setUpdateBy(AppContextUtil.getUserInfo());
            shopUserRepository.save(user);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return true;
    }


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


    /**
     * 商铺用户信息映射方法
     */
    private ShopUser toShopUserSave(ShopUserSaveParam shopUserSaveParam) {
        ShopUser shopUser = new ShopUser();
        shopUser.setShopCode(shopUserSaveParam.getShopCode());
        shopUser.setUserName(shopUserSaveParam.getUserName());
        shopUser.setPassword(passwordEncoder.encode(appProperties.getDefaultPassword()));
        shopUser.setNickName(shopUserSaveParam.getNickName());
        shopUser.setRealName(shopUserSaveParam.getRealName());
        shopUser.setEmail(shopUserSaveParam.getEmail());
        shopUser.setHeadUrl(shopUserSaveParam.getAvatar());
        shopUser.setSex(shopUserSaveParam.getSex());
        shopUser.setPhone(shopUserSaveParam.getPhone());
        return shopUser;
    }
}
