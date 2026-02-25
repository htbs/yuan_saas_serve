package com.yuansaas.app.plan.api;

import com.yuansaas.app.feature.vo.FeatureMenuBriefVo;
import com.yuansaas.app.plan.params.AssignPlanFeatureParam;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    @SecurityAuth(permissions = "users:plan:create")
    public ResponseEntity<ResponseModel<Boolean>> create(@RequestBody @Validated PlanCreateParam planCreateParam) {
        return ResponseBuilder.okResponse(planService.create(planCreateParam));
    }

    /**
     * 编辑套餐
     * @param planUpdateParam 套餐编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    @PutMapping
    @SecurityAuth(permissions = "users:plan:update")
    public ResponseEntity<ResponseModel<Boolean>> update(@RequestBody @Validated PlanUpdateParam planUpdateParam) {
        return ResponseBuilder.okResponse(planService.update(planUpdateParam));
    }

    /**
     * 禁用套餐
     * @param planId 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    @PutMapping(value = "/disable/{id}")
    @SecurityAuth(permissions = "users:plan:lock")
    public ResponseEntity<ResponseModel<Boolean>> disable( @PathVariable(value = "id") Long planId) {
        return ResponseBuilder.okResponse(planService.lock(planId));
    }
    /**
     * 启用套餐
     * @param planId 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    @PutMapping(value = "/enable/{id}")
    @SecurityAuth(permissions = "users:plan:lock")
    public ResponseEntity<ResponseModel<Boolean>> enable( @PathVariable(value = "id") Long planId) {
        return ResponseBuilder.okResponse(planService.lock(planId));
    }

    /**
     * 删除套餐
     * @param planId 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    @DeleteMapping("/delete/{id}")
    @SecurityAuth(permissions = "users:plan:delete")
    public ResponseEntity<ResponseModel<Boolean>> delete( @PathVariable(value = "id") Long planId) {
        return ResponseBuilder.okResponse(planService.delete(planId));
    }

    /**
     * 套餐分页列表查询
     * @param findPlanParam 套餐编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    @GetMapping
    @SecurityAuth(permissions = "users:plan:find")
    public ResponseEntity<ResponseModel<RPage<PlanPageListVo>>> findByPage(FindPlanParam findPlanParam) {
        return ResponseBuilder.okResponse(planService.findByPage(findPlanParam));
    }


    /**
     * 分配功能给套餐
     * @param assignPlanFeatureParam 套餐编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    @PutMapping(value = "/assign/feature")
    @SecurityAuth(permissions = "users:assign:plan-feature")
    public ResponseEntity<ResponseModel<Boolean>> assignPlanFeature(@RequestBody @Validated AssignPlanFeatureParam assignPlanFeatureParam) {
        return ResponseBuilder.okResponse(planService.assignPlanFeature(assignPlanFeatureParam));
    }

    /**
     * 获取分配给套餐的功能列表
     * @param planCode 套餐code
     * @author  lxz 2026/01/29 14:35
     */
    @GetMapping( value = "/assign/feature/list"   )
    @SecurityAuth(permissions = "users:assign:plan-feature")
    public ResponseEntity<ResponseModel<List<FeatureMenuBriefVo>>> findAssignFeatureListByPlanCode(@RequestParam String planCode) {
        return ResponseBuilder.okResponse(planService.findAssignFeatureListByPlanCode(planCode));
    }


}
