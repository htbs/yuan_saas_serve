package com.yuansaas.user.users.api;

import com.yuansaas.common.enums.UserTypeEnum;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.users.param.*;
import com.yuansaas.user.users.service.ShopUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 商家用户管理api
 *
 * @author LXZ 2026/1/21 16:37
 */
@RestController
@RequestMapping("/shop/user")
@RequiredArgsConstructor
public class ShopUserApi {

    private final ShopUserService shopUserService;

    /**
     * 创建用户
     * @param shopUserSaveParam 用户创建请求
     * @return 创建成功的用户信息
     */
    @PostMapping("/create")
    @SecurityAuth(userTypes = {UserTypeEnum.MERCHANT_USER} , permissions = "shop:users:create")
    public ResponseEntity<ResponseModel<Boolean>> createUser(@RequestBody @Validated ShopUserSaveParam shopUserSaveParam) {
        return ResponseBuilder.okResponse( shopUserService.createUser(shopUserSaveParam));
    }

    /**
     * 修改用户
     * @param userUpdateParam 用户修改请求
     * @return 修改成功的用户信息
     */
    @PostMapping("/update")
    @SecurityAuth(userTypes = {UserTypeEnum.MERCHANT_USER} , permissions = "shop:users:update")
    public ResponseEntity<ResponseModel<Boolean>> updateUser(@RequestBody @Validated ShopUserUpdateParam userUpdateParam) {
        shopUserService.updateUser(userUpdateParam);
        return ResponseBuilder.okResponse(true);
    }

    /**
     * 修改密码
     * @param updateUserPwd 用户修改请求
     * @return 修改成功的用户信息
     */
    @PostMapping("/update/pwd")
    @SecurityAuth(userTypes = {UserTypeEnum.MERCHANT_USER} , permissions = "shop:users:update_pwd")
    public ResponseEntity<ResponseModel<Boolean>> updateUserPwd(@RequestBody @Validated UpdateUserPwdParam updateUserPwd) {
        return ResponseBuilder.okResponse( shopUserService.updateUserPwd(updateUserPwd));
    }

    /**
     * 重置密码
     * @param id 用户修改请求
     * @return 修改成功的用户信息
     */
    @PutMapping("/reset/pwd/{id}")
    @SecurityAuth(authenticated = false ,userTypes = {UserTypeEnum.MERCHANT_USER} , permissions = "shop:users:reset_pwd")
    public ResponseEntity<ResponseModel<Boolean>> resetUserResetPwd(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse( shopUserService.resetUserResetPwd(id));
    }


    /**
     * 冻结用户
     * @param id 用户id
     * @return 冻结成功的用户信息 true or false
     */
    @PutMapping("/lock/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "shop:users:lock")
    public ResponseEntity<ResponseModel<Boolean>> lockUser(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse(shopUserService.lockUser(id));
    }

    /**
     * 解锁用户
     * @param id 用户id
     * @return 解释成功的用户信息 true or false
     */
    @PutMapping("/unlock/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "shop:users:lock")
    public ResponseEntity<ResponseModel<Boolean>> unlockUser(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse( shopUserService.unlockUser(id));
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return  删除成功的用户信息 true or false
     */
    @DeleteMapping("/delete/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "shop:users:delete")
    public ResponseEntity<ResponseModel<Boolean>> deleteUser(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse( shopUserService.deleteUser(id));
    }
}
