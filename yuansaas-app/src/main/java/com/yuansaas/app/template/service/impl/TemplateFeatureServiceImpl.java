package com.yuansaas.app.template.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.common.enums.IndustryTypeEnum;
import com.yuansaas.app.feature.entity.Feature;
import com.yuansaas.app.feature.entity.QFeature;
import com.yuansaas.app.feature.enums.FeatureTypeEnum;
import com.yuansaas.app.feature.service.FeatureService;
import com.yuansaas.app.template.entity.QTemplateFeature;
import com.yuansaas.app.template.entity.TemplateFeature;
import com.yuansaas.app.template.params.AssignTemplateFeatureParam;
import com.yuansaas.app.template.params.FindTemplateFeatureRPageParam;
import com.yuansaas.app.template.repository.TemplateFeatureRepository;
import com.yuansaas.app.template.service.TemplateFeatureService;
import com.yuansaas.app.template.vo.TemplateFeaturePageVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * 模版功能关系  - server
 *
 * @author LXZ 2026/2/6 12:07
 */
@Service
@RequiredArgsConstructor
public class TemplateFeatureServiceImpl implements TemplateFeatureService {

    private final FeatureService featureService;
    private final TemplateFeatureRepository templateFeatureRepository;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 给模版初始化功能
     *
     * @param templateCode 模版code
     * @param industryType 行业类型
     */
    @Override
    public void init(String templateCode, IndustryTypeEnum industryType) {
        // 获取行业下所有的功能
        List<Feature> features = featureService.getFeatureCodesByIndustryType(industryType.getName());
        // 保存模版及功能关系
        saveAssignTemplateFeatureLink(features , templateCode);
    }

    /**
     * 分配功能
     *
     * @param assignTemplateFeatureParam 分配参数
     */
    @Override
    public Boolean assignFeature(AssignTemplateFeatureParam assignTemplateFeatureParam) {
        List<Feature> featureListByFeatureCodes = featureService.getFeatureListByFeatureCodes(assignTemplateFeatureParam.getFeatureCodes());
        saveAssignTemplateFeatureLink(featureListByFeatureCodes , assignTemplateFeatureParam.getTemplateCode());
        return true;
    }

    /**
     * 移除功能
     *
     * @param assignTemplateFeatureParam 删除参数
     */
    @Override
    public Boolean removeFeature(@Valid AssignTemplateFeatureParam assignTemplateFeatureParam) {
        templateFeatureRepository.removeByFeatureCodesAndTemplateCode(assignTemplateFeatureParam.getTemplateCode() , assignTemplateFeatureParam.getFeatureCodes());
        return true;
    }

    /**
     * 禁用 / 启用 操作
     *
     * @param id 关系id
     */
    @Override
    public Boolean lock(Long id) {
        templateFeatureRepository.findById(id).ifPresentOrElse(templateFeature -> {
                    templateFeature.setLockStatus(AppConstants.N.equals(templateFeature.getLockStatus()) ? AppConstants.Y : AppConstants.N);
                    templateFeatureRepository.save(templateFeature);
                }
        ,()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("不存在的功能");
        });
        return true;
    }

    @Override
    public RPage<TemplateFeaturePageVo> getFeatureByPage(FindTemplateFeatureRPageParam findTemplateFeatureRpageParam) {

        QFeature qFeature = QFeature.feature;
        QTemplateFeature qTemplateFeature = QTemplateFeature.templateFeature;
        return findTemplateFeatureRpageParam.getPage(()->
                        jpaQueryFactory.select(Projections.bean(TemplateFeaturePageVo.class,
                           qTemplateFeature.id,
                           qFeature.featureName,
                           qFeature.featureCode,
                           qFeature.description,
                           qTemplateFeature.lockStatus
                           ))
                    .from(qTemplateFeature)
                    .leftJoin(qFeature).on(qTemplateFeature.featureCode.eq(qFeature.featureCode))
                    .where(querydsl(qFeature,qTemplateFeature,findTemplateFeatureRpageParam))
                    ,
                ()-> jpaQueryFactory.select(qTemplateFeature.id.countDistinct())
                    .from(qTemplateFeature)
                    .leftJoin(qFeature).on(qTemplateFeature.featureCode.eq(qFeature.featureCode))
                    .where(querydsl(qFeature,qTemplateFeature,findTemplateFeatureRpageParam)));
    }

    @Override
    public List<TemplateFeaturePageVo> getFeatureListByTemplateCode(List<String> templateCode) {
        QFeature qFeature = QFeature.feature;
        QTemplateFeature qTemplateFeature = QTemplateFeature.templateFeature;
        return jpaQueryFactory.select(Projections.bean(TemplateFeaturePageVo.class,
                        qTemplateFeature.id,
                        qFeature.featureName,
                        qFeature.featureCode,
                        qFeature.description,
                        qTemplateFeature.lockStatus
                ))
                .from(qTemplateFeature)
                .leftJoin(qFeature).on(qTemplateFeature.featureCode.eq(qFeature.featureCode))
                .where(querydsl(qFeature,qTemplateFeature,FindTemplateFeatureRPageParam.builder().templateCodes(templateCode).build()))
                .fetch();
    }


    /**
     * 保存模版及功能关系
     */
    private void saveAssignTemplateFeatureLink (List<Feature> featureList , String templateCode) {
        if (ObjectUtil.isNotEmpty(featureList)) {
            // 获取模版下的功能列表
            List<String> featureCodeList = templateFeatureRepository.findFeatureCodeListByTemplateCode(templateCode);
            if (ObjectUtil.isNotEmpty(featureCodeList)) {
                featureList.removeIf(f ->featureCodeList.contains(f.getFeatureCode()));
            }
            List<TemplateFeature> templateFeatureList = new ArrayList<>();
            featureList.forEach(f ->{
                TemplateFeature templateFeature = new TemplateFeature();
                templateFeature.setFeatureCode(f.getFeatureCode());
                templateFeature.setTemplateCode(templateCode);
                templateFeature.setIsDefault(FeatureTypeEnum.INIT.matches(f.getFeatureType()) ? AppConstants.Y : AppConstants.N);
                templateFeature.init();
                templateFeatureList.add(templateFeature);
            });
            // 保存
            templateFeatureRepository.saveAll(templateFeatureList);
        }
    }

    /**
     * 构建查询条件
     */
    private BooleanBuilder querydsl (QFeature qFeature , QTemplateFeature qTemplateFeature , FindTemplateFeatureRPageParam findTemplateFeatureRpageParam) {
        return BoolBuilder.getInstance()
                .and(findTemplateFeatureRpageParam.getFeatureName() ,  qFeature.featureName::contains)
                .and(findTemplateFeatureRpageParam.getTemplateCode() , qTemplateFeature.templateCode::eq)
                .and(findTemplateFeatureRpageParam.getTemplateCodes() , qTemplateFeature.templateCode::in)
                .and(findTemplateFeatureRpageParam.getLockStatus() , qTemplateFeature.lockStatus::eq)
                .getWhere();
    }

}
