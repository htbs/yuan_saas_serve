package com.yuansaas.app.plan.api;

import com.yuansaas.app.feature.params.FeatureCreateParam;
import com.yuansaas.app.feature.params.FeatureUpdateParam;
import com.yuansaas.app.feature.service.FeatureService;
import com.yuansaas.app.order.shop.vo.ShopOrderCountVo;
import com.yuansaas.app.plan.params.FindPlanParam;
import com.yuansaas.app.plan.params.PlanCreateParam;
import com.yuansaas.app.plan.params.PlanUpdateParam;
import com.yuansaas.app.plan.service.PlanService;
import com.yuansaas.app.plan.vo.PlanPageListVo;
import com.yuansaas.core.page.RPage;
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
 * 后台操作 - 套餐管理api
 *
 * @author LXZ 2026/1/22 17:07
 */
@RequestMapping("/plan")
@RestController
@RequiredArgsConstructor
public class PlanApi {

    private final PlanService planService;

    /**
     * 新增套餐
     * @param planCreateParam 套餐保存参数
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.POST)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:plan:create')")
    public ResponseEntity<ResponseModel<Boolean>> create(@RequestBody @Validated PlanCreateParam planCreateParam) {
        return ResponseBuilder.okResponse(planService.create(planCreateParam));
    }

    /**
     * 编辑套餐
     * @param planUpdateParam 套餐编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.PUT)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:plan:update')")
    public ResponseEntity<ResponseModel<Boolean>> update(@RequestBody @Validated PlanUpdateParam planUpdateParam) {
        return ResponseBuilder.okResponse(planService.update(planUpdateParam));
    }

    /**
     * 禁用套餐
     * @param planId 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.PUT ,value = "/lock/{id}")
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:plan:lock')")
    public ResponseEntity<ResponseModel<Boolean>> lock( @PathVariable Long planId) {
        return ResponseBuilder.okResponse(planService.lock(planId));
    }
    /**
     * 启用套餐
     * @param planId 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.PUT ,value = "/lock/{id}")
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:plan:lock')")
    public ResponseEntity<ResponseModel<Boolean>> lock( @PathVariable Long planId) {
        return ResponseBuilder.okResponse(planService.lock(planId));
    }

    /**
     * 禁用套餐
     * @param planId 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/delete/{id}")
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:plan:delete')")
    public ResponseEntity<ResponseModel<Boolean>> delete( @PathVariable Long planId) {
        return ResponseBuilder.okResponse(planService.delete(planId));
    }

    /**
     * 套餐分页列表查询
     * @param findPlanParam 套餐编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    @RequestMapping(method = RequestMethod.GET)
    @SecurityAuth
    @PreAuthorize("@ss.hasPermission('system:plan:find')")
    public ResponseEntity<ResponseModel<RPage<PlanPageListVo>>> findByPage(FindPlanParam findPlanParam) {
        return ResponseBuilder.okResponse(planService.findByPage(findPlanParam));
    }

}
