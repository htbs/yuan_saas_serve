package com.yuansaas.app.feature.api;

import com.yuansaas.app.feature.params.AssignFeatureMenuParam;
import com.yuansaas.app.feature.params.FeatureCreateParam;
import com.yuansaas.app.feature.params.FeatureUpdateParam;
import com.yuansaas.app.feature.service.FeatureService;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 后台操作  - 功能管理
 *
 * @author LXZ 2026/1/22 17:07
 */
@RequestMapping("/feature")
@RestController
@RequiredArgsConstructor
public class FeatureApi {

    private final FeatureService featureService;

    /**
     * 新增功能
     * @param featureCreateParam 功能保存参数
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.POST)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:feature:create')")
    public ResponseEntity<ResponseModel<Boolean>> create(@RequestBody @Validated FeatureCreateParam featureCreateParam) {
        return ResponseBuilder.okResponse(featureService.create(featureCreateParam));
    }

    /**
     * 编辑功能
     * @param featureUpdateParam 功能编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.PUT)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:feature:update')")
    public ResponseEntity<ResponseModel<Boolean>> update(@RequestBody @Validated FeatureUpdateParam featureUpdateParam) {
        return ResponseBuilder.okResponse(featureService.update(featureUpdateParam));
    }


    /**
     * 分配菜单给功能点
     *
     * @param assignFeatureMenuParam 功能编辑参数
     * @author lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.PUT)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:feature:assign-feature-menu')")
    public ResponseEntity<ResponseModel<Boolean>> assignFeatureMenu(@RequestBody @Validated AssignFeatureMenuParam assignFeatureMenuParam) {
        return ResponseBuilder.okResponse(featureService.assignFeatureMenu(assignFeatureMenuParam));
    }

    /**
     * 获取功能点下分配的菜单code列表
     *
     * @param featureCode  功能code
     * @author lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.GET)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:featureId:assign-featureId-menu')")
    public ResponseEntity<ResponseModel<List<Long>>> getFeatureMenuListByFeature(@RequestParam(name = "featureCode") String featureCode) {
        return ResponseBuilder.okResponse(featureService.getMenuCodeListByFeatureCodeAndLockStatus(featureCode, AppConstants.N));
    }

}
