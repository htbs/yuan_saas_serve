package com.yuansaas.app.plan.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.plan.entity.Plan;
import com.yuansaas.app.plan.entity.QPlan;
import com.yuansaas.app.plan.params.FindPlanParam;
import com.yuansaas.app.plan.params.PlanCreateParam;
import com.yuansaas.app.plan.params.PlanUpdateParam;
import com.yuansaas.app.plan.repository.PlanRepository;
import com.yuansaas.app.plan.service.PlanService;
import com.yuansaas.app.plan.vo.PlanPageListVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *
 * 套餐 service 实现类
 *
 * @author LXZ 2026/2/1 16:45
 */
@Service
@AllArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final JPAQueryFactory jpaQueryFactory;
    /**
     * 新增套餐
     *
     * @param planCreateParam 套餐保存参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean create(PlanCreateParam planCreateParam) {
        Plan plan = new Plan();
        BeanUtils.copyProperties(planCreateParam  , plan);
        plan.setPlanCode(getPlanCode());
        plan.setLockStatus(AppConstants.N);
        plan.setDeleteStatus(AppConstants.N);
        plan.init();
        planRepository.save(plan);
        return true;
    }

    /**
     * 编辑套餐
     *
     * @param planUpdateParam 套餐编辑参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean update(PlanUpdateParam planUpdateParam) {
        planRepository.findById(planUpdateParam.getId()).ifPresentOrElse(plan -> {
            BeanUtils.copyProperties(planUpdateParam, plan);
            plan.update();
            planRepository.save(plan);
            }
        ,()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("套餐数据不存在");
                });
        return true;
    }

    /**
     * 操作套餐
     *
     * @param id 套餐id
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean lock(Long id) {
        planRepository.findById(id).ifPresentOrElse(plan -> {
                    plan.setLockStatus(AppConstants.N.equals(plan.getLockStatus()) ? AppConstants.Y : AppConstants.N);
                    plan.update();
                    planRepository.save(plan);
                }
                ,()->{
                    throw DataErrorCode.DATA_NOT_FOUND.buildException("套餐数据不存在");
                });
        return true;
    }

    /**
     * 删除套餐
     *
     * @param id 套餐id
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean delete(Long id) {
        planRepository.findById(id).ifPresentOrElse(plan -> {
                    plan.setDeleteStatus(AppConstants.Y);
                    plan.update();
                    planRepository.save(plan);
                }
                ,()->{
                    throw DataErrorCode.DATA_NOT_FOUND.buildException("套餐数据不存在");
                });
        return true;
    }

    /**
     * 套餐列表查询
     *
     * @param findPlanParam 套餐查询参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public RPage<PlanPageListVo> findByPage(FindPlanParam findPlanParam) {

        QPlan plan = QPlan.plan;
        BooleanBuilder boolBuilder = BoolBuilder.getInstance()
                .and(findPlanParam.getPlanName() ,plan.planName::contains)
                .and(findPlanParam.getPlanType() , plan.planType::eq)
                .and(findPlanParam.getBillingType() , plan.billingType::eq)
                .and(findPlanParam.getLockStatus() , plan.lockStatus::eq)
                .getWhere();

        return findPlanParam.getPage(()->{
           return jpaQueryFactory.select(Projections.bean(PlanPageListVo.class,
                            plan.id,
                    plan.planName,
                    plan.planType,
                    plan.billingType,
                    plan.description,
                    plan.validCount,
                    plan.countUnit,
                    plan.planAmount,
                    plan.lockStatus,
                    plan.updateBy,
                    plan.updateAt
                            ))
                    .from(plan)
                    .where(boolBuilder);
        },()->{
            return jpaQueryFactory.select(plan.countDistinct())
                    .from(plan)
                    .where(boolBuilder)
                    ;
        });
    }

    /**
     * 生成功能code
     */
    public String getPlanCode() {
        String code = RandomUtil.randomStringUpper(AppConstants.FOUR);
        if (validatedPlanCodeIsExists(code)) {
            getPlanCode();
        }
        return code ;
    }

    /**
     * 验证功能code是否存在
     * @param featureCode 功能code
     */
    private Boolean validatedPlanCodeIsExists(String featureCode) {
        return planRepository.countByPlanCode(featureCode) > 0;
    }
}
