package com.yuansaas.user.system.api;

import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.common.enums.UserType;
import com.yuansaas.user.system.entity.SysUser;
import com.yuansaas.user.system.service.SysUserService;
import com.yuansaas.user.system.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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

//    /**
//     * 创建用户
//     * @param request 用户创建请求
//     * @return 创建成功的用户信息
//     */
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ResponseModel<SysUser>> createUser(@RequestBody @Valid  SysUserCreateRequest request) {
//        SysUser user = userService.createUser(request);
//        return ResponseBuilder.okResponse( user);
//    }

}
