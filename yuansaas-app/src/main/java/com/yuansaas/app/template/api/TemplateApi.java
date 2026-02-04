package com.yuansaas.app.template.api;

import com.yuansaas.app.template.params.FindTemplateRPageParam;
import com.yuansaas.app.template.params.TemplateCreateParam;
import com.yuansaas.app.template.params.TemplateUpdateParam;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 后台管理 -  模版操作api
 *
 * @author LXZ 2026/1/22 17:07
 */
@RequestMapping("/template")
@RestController
@RequiredArgsConstructor
public class TemplateApi {

    /**
     * 创建模版
     * @param templateCreateParam 模版新增参数
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:template:create')")
    public ResponseEntity<ResponseModel<Boolean>> add(@RequestBody @Validated TemplateCreateParam templateCreateParam) {
        return ResponseBuilder.okResponse(null);
    }

    /**
     * 修改模版
     * @param templateUpdateParam 模版修改参数
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:template:update')")
    public ResponseEntity<ResponseModel<Boolean>> update(@RequestBody @Validated TemplateUpdateParam templateUpdateParam) {
        return ResponseBuilder.okResponse(null);
    }

    /**
     * 禁用模版
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/disable/{id}",method = RequestMethod.PUT)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:template:lock')")
    public ResponseEntity<ResponseModel<Boolean>> disable(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(null);
    }

    /**
     * 启用模版
     * @param id 模版id
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/enable/{id}",method = RequestMethod.PUT)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:template:lock')")
    public ResponseEntity<ResponseModel<Boolean>> enable(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(null);
    }

    /**
     * 删除模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:template:delete')")
    public ResponseEntity<ResponseModel<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(null);
    }

    /**
     * 获取模版分页列表
     * @param findTemplateRPageParam 模版
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:template:find')")
    public ResponseEntity<ResponseModel<Boolean>> getByRPage(@RequestBody FindTemplateRPageParam findTemplateRPageParam) {
        return ResponseBuilder.okResponse(null);
    }

    /**
     * 获取模版详情
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:template:find')")
    public ResponseEntity<ResponseModel<Boolean>> getInfoById(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(null);
    }

}
