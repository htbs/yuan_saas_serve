package com.yuansaas.user.users.api;

import com.yuansaas.common.enums.UserTypeEnum;
import com.yuansaas.core.page.RPage;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.menu.vo.MenuListVo;
import com.yuansaas.user.permission.service.PermissionService;
import com.yuansaas.user.users.entity.SysUser;
import com.yuansaas.user.users.param.FindUserParam;
import com.yuansaas.user.users.param.SysUserCreateParam;
import com.yuansaas.user.users.param.UpdateUserPwdParam;
import com.yuansaas.user.users.param.UserUpdateParam;
import com.yuansaas.user.users.service.SysUserService;
import com.yuansaas.user.users.vo.SysUserListVo;
import com.yuansaas.user.users.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统用户API(平台用户管理)
 *
 * @author HTB 2025/8/5 09:44
 */
@RestController
@RequestMapping("/sys/users")
@RequiredArgsConstructor
public class SysUserApi {

    private final SysUserService userService;
    private final PermissionService permissionService;

    /**
     * 创建用户
     * @param sysUserCreateParam 用户创建请求
     * @return 创建成功的用户信息
     */
    @PostMapping("/save")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:create")
    public ResponseEntity<ResponseModel<SysUser>> createUser(@RequestBody @Validated SysUserCreateParam sysUserCreateParam) {
        SysUser user = userService.saveUser(sysUserCreateParam);
        return ResponseBuilder.okResponse( user);
    }

    /**
     * 修改用户
     * @param userUpdateParam 用户修改请求
     * @return 修改成功的用户信息
     */
    @PostMapping("/update")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:update")
    public ResponseEntity<ResponseModel<Boolean>> updateUser(@RequestBody @Validated UserUpdateParam userUpdateParam) {
        return ResponseBuilder.okResponse( userService.updateUser(userUpdateParam));
    }

    /**
     * 修改密码
     * @param updateUserPwd 用户修改请求
     * @return 修改成功的用户信息
     */
    @PostMapping("/update/pwd")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:update_pwd")
    public ResponseEntity<ResponseModel<Boolean>> updateUserPwd(@RequestBody @Validated UpdateUserPwdParam updateUserPwd) {
        return ResponseBuilder.okResponse( userService.updateUserPwd(updateUserPwd));
    }

    /**
     * 重置密码
     * @param id 用户修改请求
     * @return 修改成功的用户信息
     */
    @PutMapping("/reset/pwd/{id}")
    @SecurityAuth(authenticated = false ,userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:reset_pwd")
    public ResponseEntity<ResponseModel<Boolean>> resetUserResetPwd(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse( userService.resetUserResetPwd(id));
    }


    /**
     * 冻结用户
     * @param id 用户id
     * @return 冻结成功的用户信息 true or false
     */
    @PutMapping("/lock/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:lock")
    public ResponseEntity<ResponseModel<Boolean>> lockUser(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse(userService.lockUser(id));
    }

    /**
     * 解锁用户
     * @param id 用户id
     * @return 解释成功的用户信息 true or false
     */
    @PutMapping("/unlock/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:lock")
    public ResponseEntity<ResponseModel<Boolean>> unlockUser(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse( userService.unlockUser(id));
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return  删除成功的用户信息 true or false
     */
    @DeleteMapping("/delete/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:delete")
    public ResponseEntity<ResponseModel<Boolean>> deleteUser(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse( userService.deleteUser(id));
    }


    /**
     * 用户可访问的菜单列表
     * @param id 用户id
     * @return  用户可访问的菜单列表
     */
    @GetMapping("/get/menu/list/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER,UserTypeEnum.MERCHANT_USER})
    public ResponseEntity<ResponseModel<List<MenuListVo>>> findMenuListByUserId(@PathVariable(name = "id") Long id) {
        return ResponseBuilder.okResponse( permissionService.findMenuListByUserId(id));
    }

    /**
     * 列表查询
     * @param findUserParam 查询参数
     * @return roleListVo
     */
    @GetMapping("/page")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:find")
    public ResponseEntity<ResponseModel<RPage<SysUserListVo>>> getByPage(FindUserParam findUserParam) {
        return ResponseBuilder.okResponse(userService.getByPage(findUserParam));
    }
    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:users:find")
    public ResponseEntity<ResponseModel<SysUserVo>> getUserById(@PathVariable Long id) {
        return ResponseBuilder.okResponse(userService.findLinkDateById(id));
    }


}
