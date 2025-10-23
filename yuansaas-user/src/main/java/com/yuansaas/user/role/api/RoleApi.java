package com.yuansaas.user.role.api;

import com.yuansaas.core.page.RPage;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.role.params.FindRoleParam;
import com.yuansaas.user.role.params.SaveRoleParam;
import com.yuansaas.user.role.params.UpdateRoleParam;
import com.yuansaas.user.role.service.RoleService;
import com.yuansaas.user.role.vo.RoleListVo;
import com.yuansaas.user.role.vo.RoleVo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 角色管理
 *
 * @author LXZ 2025/10/21 10:26
 */
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleApi {

    private final RoleService roleService;

    /**
     * 列表查询
     * @param findRoleParam 查询参数
     * @return roleListVo
     */
    @GetMapping("/page")
    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<RPage<RoleListVo>>> getByPage(FindRoleParam findRoleParam) {
        return ResponseBuilder.okResponse(roleService.getByPage(findRoleParam));
    }

    /**
     * 新增角色
     * @param saveRoleParam 新增参数
     * @return true/false
     */
    @PostMapping("/save")
    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<Boolean>> save(@Validated @RequestBody SaveRoleParam saveRoleParam) {
        return ResponseBuilder.okResponse(roleService.save(saveRoleParam));
    }
    /**
     * 修改角色
     * @param updateRoleParam 修改参数
     * @return true/false
     */
    @PutMapping("/update")
    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<Boolean>> update(@Validated @RequestBody UpdateRoleParam updateRoleParam) {
        return ResponseBuilder.okResponse(roleService.update(updateRoleParam));
    }
    /**
     * 删除角色
     * @param id 角色ID
     * @return true/false
     */
    @GetMapping("/delete/{id}")
    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<Boolean>> delete(@PathVariable("id") Long id ) {
        return ResponseBuilder.okResponse(roleService.delete(id));
    }
    /**
     * 角色详情
     * @param id 角色ID
     * @return roleVo
     */
    @GetMapping("/{id}")
    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<RoleVo>> getById(@PathVariable("id") Long id ) {
        return ResponseBuilder.okResponse(roleService.getById(id));
    }
//    /**
//     * 角色授权
//     * @param id
//     */
//    @GetMapping("/authorize")
//    @SecurityAuth(authenticated = false)
//    public ResponseEntity<ResponseModel<Boolean>> authorize(@RequestPart("id") Long id ) {
//        return ResponseBuilder.okResponse(roleService.authorize(id));
//    }
}
