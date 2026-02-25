package com.yuansaas.app.template.api;

import com.yuansaas.app.template.params.*;
import com.yuansaas.app.template.service.TemplateFeatureService;
import com.yuansaas.app.template.service.TemplateService;
import com.yuansaas.app.template.vo.TemplateFeaturePageVo;
import com.yuansaas.app.template.vo.TemplateInfoVo;
import com.yuansaas.app.template.vo.TemplatePageVo;
import com.yuansaas.common.enums.UserTypeEnum;
import com.yuansaas.core.page.RPage;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private final TemplateService templateService;
    private final TemplateFeatureService templateFeatureService;

    /**
     * 创建模版
     * @param templateCreateParam 模版新增参数
     * @author  lxz 2025/11/16 14:35
     */
    @PostMapping(value = "/create")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:create")
    public ResponseEntity<ResponseModel<Boolean>> add(@RequestBody @Validated TemplateCreateParam templateCreateParam) {
        return ResponseBuilder.okResponse(templateService.add(templateCreateParam));
    }

    /**
     * 修改模版
     * @param templateUpdateParam 模版修改参数
     * @author  lxz 2025/11/16 14:35
     */
    @PutMapping(value = "/update")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:update")
    public ResponseEntity<ResponseModel<Boolean>> update(@RequestBody @Validated TemplateUpdateParam templateUpdateParam) {
        return ResponseBuilder.okResponse(templateService.update(templateUpdateParam));
    }

    /**
     * 禁用模版
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @PutMapping(value = "/disable/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:lock")
    public ResponseEntity<ResponseModel<Boolean>> disable(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(templateService.lock(id));
    }

    /**
     * 启用模版
     * @param id 模版id
     * @author  lxz 2025/11/16 14:35
     */
    @PutMapping(value = "/enable/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:lock")
    public ResponseEntity<ResponseModel<Boolean>> enable(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(templateService.lock(id));
    }

    /**
     * 删除模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @DeleteMapping(value = "/delete/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:delete")
    public ResponseEntity<ResponseModel<Boolean>> delete(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(templateService.delete(id));
    }

    /**
     * 获取模版分页列表
     * @param findTemplateRpageParam 模版
     * @author  lxz 2025/11/16 14:35
     */
    @GetMapping(value = "/page")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:find")
    public ResponseEntity<ResponseModel<RPage<TemplatePageVo>>> getByRpage(FindTemplateRPageParam findTemplateRpageParam) {
        return ResponseBuilder.okResponse(templateService.getByRPage(findTemplateRpageParam));
    }

    /**
     * 获取模版详情
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @GetMapping(value = "/get/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:find")
    public ResponseEntity<ResponseModel<TemplateInfoVo>> getInfoById(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(templateService.getInfoById(id));
    }

    /**
     * 分配功能给模版
     * @param assignTemplateFeatureParam 分配参数
     * @author lxz 2025/11/16 14:35
     */
    @PostMapping(value = "/assign")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "'users:template:assign_feature_template")
    public ResponseEntity<ResponseModel<Boolean>> assignFeature(@RequestBody @Validated AssignTemplateFeatureParam assignTemplateFeatureParam) {
        return ResponseBuilder.okResponse(templateFeatureService.assignFeature(assignTemplateFeatureParam));
    }

    /**
     * 获取模版名下分配的功能列表
     * @param findTemplateFeatureRpageParam 分配参数
     * @author lxz 2025/11/16 14:35
     */
    @GetMapping(value = "/find/feature")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "users:template:assign_feature_template")
    public ResponseEntity<ResponseModel<RPage<TemplateFeaturePageVo>>> getFeatureByTemplateCode(@Validated FindTemplateFeatureRPageParam findTemplateFeatureRpageParam ){
        return ResponseBuilder.okResponse(templateFeatureService.getFeatureByPage(findTemplateFeatureRpageParam));
    }

    /**
     * 关闭功能
     * @param id 关系id
     * @author lxz 2025/11/16 14:35
     */
    @PutMapping(value = "/feature/disable/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "template:feature:lock")
    public ResponseEntity<ResponseModel<Boolean>> disableFeature(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(templateFeatureService.lock(id));
    }

    /**
     *  开启功能
     * @param id 关系id
     * @author lxz 2025/11/16 14:35
     */
    @PutMapping(value = "/feature/enable/{id}")
    @SecurityAuth(userTypes = {UserTypeEnum.YUAN_SHI_USER} , permissions = "template:feature:lock'")
    public ResponseEntity<ResponseModel<Boolean>> enableFeature(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(templateFeatureService.lock(id));
    }

    /**
     *  删除功能
     * @param assignTemplateFeatureParam 删除操作
     * @author lxz 2025/11/16 14:35
     */
    @DeleteMapping(value = "/feature/remove")
    @SecurityAuth( userTypes = {UserTypeEnum.YUAN_SHI_USER},permissions = "template:feature:remove")
    public ResponseEntity<ResponseModel<Boolean>> removeFeature(@RequestBody @Validated AssignTemplateFeatureParam assignTemplateFeatureParam) {
        return ResponseBuilder.okResponse(templateFeatureService.removeFeature(assignTemplateFeatureParam));
    }

}
