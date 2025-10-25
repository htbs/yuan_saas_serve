package com.yuansaas.user.system.api;

import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.common.enums.UserType;
import com.yuansaas.user.menu.vo.MenuListVo;
import com.yuansaas.user.system.entity.SysUser;
import com.yuansaas.user.system.param.SysUserCreateParam;
import com.yuansaas.user.system.param.UserUpdateParam;
import com.yuansaas.user.system.service.SysUserService;
import com.yuansaas.user.system.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统用户API
 *
 * @author HTB 2025/8/5 09:44
 */
@RestController
@RequestMapping("/sys/users")
@RequiredArgsConstructor
public class SysUserApi {

    private final SysUserService userService;

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @SecurityAuth(userTypes = {UserType.SYSTEM_USER})
    public ResponseEntity<ResponseModel<SysUserVo>> getUserById(@PathVariable Long id) {
        SysUser user = userService.findById(id)
                .orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在"));
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(user, sysUserVo) ;
        return ResponseBuilder.okResponse(sysUserVo);
    }

    /**
     * 创建用户
     * @param sysUserCreateParam 用户创建请求
     * @return 创建成功的用户信息
     */
    @PostMapping("/save")
//    @SecurityAuth(authenticated = false)
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
//    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<Boolean>> updateUser(@RequestBody @Validated UserUpdateParam userUpdateParam) {
        return ResponseBuilder.okResponse( userService.updateUser(userUpdateParam));
    }

    /**
     * 冻结用户
     * @param id 用户id
     * @return 冻结成功的用户信息 true or false
     */
    @PutMapping("/lcok/{id}")
        public ResponseEntity<ResponseModel<Boolean>> lockUser(@PathVariable Long id) {
        return ResponseBuilder.okResponse(userService.lockUser(id));
    }

    /**
     * 解锁用户
     * @param id 用户id
     * @return 解释成功的用户信息 true or false
     */
    @PutMapping("/unlock/{id}")
    public ResponseEntity<ResponseModel<Boolean>> unlockUser(@PathVariable Long id) {
        return ResponseBuilder.okResponse( userService.unlockUser(id));
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return  删除成功的用户信息 true or false
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseModel<Boolean>> deleteUser(@PathVariable Long id) {
        return ResponseBuilder.okResponse( userService.deleteUser(id));
    }


    /**
     * 用户可访问的菜单列表
     * @param id 用户id
     * @return  用户可访问的菜单列表
     */
    @GetMapping("/get/menu/list/{id}")
    public ResponseEntity<ResponseModel<List<MenuListVo>>> findMenuListByUserId(@PathVariable Long id) {
        return ResponseBuilder.okResponse( userService.findMenuListByUserId(id));
    }


}
