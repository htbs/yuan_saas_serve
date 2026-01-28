package com.yuansaas.user.permission.api;

import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.permission.params.AuthorizeMenuParam;
import com.yuansaas.user.permission.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 权限 Controller，提供赋予用户、角色的权限的 API 接口
 *
 * @author LXZ 2025/10/21 10:26
 */
@RestController
@RequestMapping("/permission")
@AllArgsConstructor
public class PermissionApi {

    private final PermissionService permissionService;

    /**
     * 分配菜单给角色
     * @param authorizeMenuParam 分配参数
     * @return true/false
     */
    @PostMapping("/assign/role/menu")
    @SecurityAuth()
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public ResponseEntity<ResponseModel<Boolean>> assignRoleMenu(@RequestBody @Validated AuthorizeMenuParam authorizeMenuParam) {
        return ResponseBuilder.okResponse(permissionService.assignRoleMenu(authorizeMenuParam));
    }

    /**
     * 获取角色拥有的菜单code
     * @param roleId 角色ID
     * @return Long
     */
    @GetMapping("/assign/role/menu/list/{roleId}")
    @SecurityAuth()
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public ResponseEntity<ResponseModel<List<Long>>> getRoleMenuListByRoleId(@PathVariable("roleId") Long roleId) {
        return ResponseBuilder.okResponse(permissionService.getRoleMenuListByRoleId(roleId));
    }




}
