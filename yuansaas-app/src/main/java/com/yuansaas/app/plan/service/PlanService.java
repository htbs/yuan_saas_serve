package com.yuansaas.app.plan.service;

import com.yuansaas.app.feature.vo.FeatureMenuBriefVo;
import com.yuansaas.app.plan.params.AssignPlanFeatureParam;
import com.yuansaas.app.plan.params.FindPlanParam;
import com.yuansaas.app.plan.params.PlanCreateParam;
import com.yuansaas.app.plan.params.PlanUpdateParam;
import com.yuansaas.app.plan.vo.PlanPageListVo;
import com.yuansaas.core.page.RPage;
import jakarta.validation.Valid;

import java.util.List;

/**
 *
 * 套餐配置- Service 接口
 *
 * @author LXZ 2026/2/1 15:46
 */
public interface PlanService {

    /**
     * 新增套餐
     * @param planCreateParam 套餐保存参数
     * @author  lxz 2026/01/29 14:35
     */
    Boolean create(@Valid PlanCreateParam planCreateParam);

    /**
     * 编辑套餐
     * @param planUpdateParam 套餐编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    Boolean update(@Valid PlanUpdateParam planUpdateParam);

    /**
     * 操作套餐
     * @param id 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    Boolean lock(Long id);

    /**
     * 删除套餐
     * @param id 套餐id
     * @author  lxz 2026/01/29 14:35
     */
    Boolean delete(Long id);

    /**
     * 套餐列表查询
     * @param findPlanParam 套餐查询参数
     * @author  lxz 2026/01/29 14:35
     */
    RPage<PlanPageListVo> findByPage(FindPlanParam findPlanParam);

    /**
     * 分配功能给套餐
     *
     * @param assignPlanFeatureParam 分配参数
     * @author lxz 2026/01/29 14:35
     */
    Boolean assignPlanFeature(@Valid AssignPlanFeatureParam assignPlanFeatureParam);

    /**
     * 获取分配给套餐的功能列表
     *
     * @param planCode 套餐code
     * @author lxz 2026/01/29 14:35
     */
    List<FeatureMenuBriefVo> findAssignFeatureListByPlanCode(String planCode);
}
