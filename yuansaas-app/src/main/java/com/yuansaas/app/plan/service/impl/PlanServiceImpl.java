package com.yuansaas.app.plan.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.feature.entity.QFeature;
import com.yuansaas.app.feature.service.FeatureService;
import com.yuansaas.app.feature.vo.FeatureMenuBriefVo;
import com.yuansaas.app.plan.entity.Plan;
import com.yuansaas.app.plan.entity.PlanFeatureLink;
import com.yuansaas.app.plan.entity.QPlan;
import com.yuansaas.app.plan.entity.QPlanFeatureLink;
import com.yuansaas.app.plan.params.AssignPlanFeatureParam;
import com.yuansaas.app.plan.params.FindPlanParam;
import com.yuansaas.app.plan.params.PlanCreateParam;
import com.yuansaas.app.plan.params.PlanUpdateParam;
import com.yuansaas.app.plan.repository.PlanFeatureLinkRepository;
import com.yuansaas.app.plan.repository.PlanRepository;
import com.yuansaas.app.plan.service.PlanService;
import com.yuansaas.app.plan.vo.PlanPageListVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import com.yuansaas.user.menu.vo.MenuVo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final FeatureService featureService;
    private final PlanFeatureLinkRepository planFeatureLinkRepository;

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
     * 分配功能给套餐
     *
     * @param assignPlanFeatureParam 分配参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean assignPlanFeature(@Valid AssignPlanFeatureParam assignPlanFeatureParam) {
        Integer count = planRepository.countByPlanCode(assignPlanFeatureParam.getPlanCode());
        if (count == 0) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException("套餐不存在");
        }
        List<String> featureCodeIn = featureService.getFeatureCodeListByFeatureCodes(assignPlanFeatureParam.getFeatureCode());
        if (ObjectUtil.isEmpty(featureCodeIn)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException("功能不存在");
        }
        // 组装数据
        List<PlanFeatureLink> planFeatureLinks = new ArrayList<>();
        featureCodeIn.forEach(f ->{
            PlanFeatureLink planFeatureLink = new PlanFeatureLink();
            planFeatureLink.setFeatureCode(f);
            planFeatureLink.setPlanCode(assignPlanFeatureParam.getPlanCode());
            planFeatureLink.create();
            planFeatureLinks.add(planFeatureLink);
        });
        planFeatureLinkRepository.saveAll(planFeatureLinks);

        return true;
    }

    /**
     * 获取分配给套餐的功能列表
     *
     * @param planCode 套餐code
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public List<FeatureMenuBriefVo> findAssignFeatureListByPlanCode(String planCode) {

        QPlanFeatureLink qPlanFeatureLink = QPlanFeatureLink.planFeatureLink;
        QFeature qFeature = QFeature.feature;

        List<FeatureMenuBriefVo> featureMenuBriefVos = jpaQueryFactory.select(Projections.bean(FeatureMenuBriefVo.class,
                        qFeature.featureCode,
                        qFeature.featureName,
                        qFeature.featureType,
                        qFeature.description,
                        qFeature.featureScope
                ))
                .from(qPlanFeatureLink)
                .leftJoin(qFeature).on(qPlanFeatureLink.featureCode.eq(qFeature.featureCode))
                .where(qPlanFeatureLink.planCode.eq(planCode))
                .fetch();
        if (ObjectUtil.isEmpty(featureMenuBriefVos)) {
            return null;
        }
        // 获取功能code
        List<String> featureCodes = featureMenuBriefVos.stream().map(FeatureMenuBriefVo::getFeatureCode).collect(Collectors.toList());
        Map<String, List<MenuVo>> menuListByFeatureCodesAndMenuType = featureService.getMenuListByFeatureCodesAndMenuType(featureCodes, AppConstants.ZERO);

        featureMenuBriefVos.forEach(f ->{
            if (menuListByFeatureCodesAndMenuType.containsKey(f.getFeatureCode())) {
                f.setMenuVos(menuListByFeatureCodesAndMenuType.get(f.getFeatureCode()));
            }
        });
        return featureMenuBriefVos;
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
