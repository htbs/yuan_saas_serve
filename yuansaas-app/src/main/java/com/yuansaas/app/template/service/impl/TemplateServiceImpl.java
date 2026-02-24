package com.yuansaas.app.template.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.template.entity.QTemplate;
import com.yuansaas.app.template.entity.Template;
import com.yuansaas.app.template.params.FindTemplateRPageParam;
import com.yuansaas.app.template.params.TemplateCreateParam;
import com.yuansaas.app.template.params.TemplateUpdateParam;
import com.yuansaas.app.template.repository.TemplateRepository;
import com.yuansaas.app.template.service.TemplateFeatureService;
import com.yuansaas.app.template.service.TemplateService;
import com.yuansaas.app.template.vo.TemplateInfoVo;
import com.yuansaas.app.template.vo.TemplatePageVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 模版操作 Service 实现类
 *
 * @author LXZ 2026/2/4 20:30
 */
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final TemplateFeatureService templateFeatureService;

    /**
     * 创建模版
     *
     * @param templateCreateParam 模版新增参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    @Transactional
    public Boolean add(@Valid TemplateCreateParam templateCreateParam) {
        Template template = new Template();
        BeanUtil.copyProperties(templateCreateParam , template);
        template.setTemplateCode(getCode());
        template.init();
        templateRepository.save(template);
        // 初始化功能点
        templateFeatureService.init(template.getTemplateCode() , templateCreateParam.getIndustryType());
        return true;
    }

    /**
     * 修改模版
     *
     * @param templateUpdateParam 模版修改参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean update(@Valid TemplateUpdateParam templateUpdateParam) {
        templateRepository.findById(templateUpdateParam.getTemplateId()).ifPresentOrElse(template -> {
            template.setTemplateName(templateUpdateParam.getTemplateName());
            template.setTemplateType(templateUpdateParam.getTemplateType());
            template.setCoverImage(templateUpdateParam.getCoverImage());
            template.setImagesUrl(templateUpdateParam.getImagesUrl());
            template.setVideoUrl(templateUpdateParam.getVideoUrl());
            template.setIsDefault(templateUpdateParam.getIsDefault());
            template.update();
            templateRepository.save(template);
        } , ()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return true;
    }

    /**
     * 操作模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean lock(Long id) {
        templateRepository.findById(id).ifPresentOrElse(template -> {
            template.setLockStatus(AppConstants.N.equals(template.getLockStatus()) ? AppConstants.Y : AppConstants.N);
            template.update();
            templateRepository.save(template);
        } , ()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return true;
    }

    /**
     * 删除模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean delete(Long id) {
        templateRepository.findById(id).ifPresentOrElse(template -> {
            template.setDeleteStatus(AppConstants.Y);
            template.update();
            templateRepository.save(template);
        } , ()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return true;
    }

    /**
     * 获取模版分页列表
     *
     * @param findTemplateRpageParam 模版
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public RPage<TemplatePageVo> getByRPage(FindTemplateRPageParam findTemplateRpageParam) {
        QTemplate template = QTemplate.template;

        BooleanBuilder booleanBuilder = queryDsl(template , findTemplateRpageParam);
        return  findTemplateRpageParam.getPage(() ->
             jpaQueryFactory.select(Projections.bean(TemplatePageVo.class,
                            template.id,
                            template.templateName,
                            template.templateCode,
                            template.templateType,
                            template.industryType,
                            template.imagesUrl,
                            template.videoUrl,
                            template.coverImage,
                            template.isDefault,
                            template.updateBy,
                            template.updateAt
                    )).from(template)
                    .where(booleanBuilder)
        ,()-> jpaQueryFactory.select(template.id.countDistinct()
                    ).from(template)
                    .where(booleanBuilder));
    }

    /**
     * 获取模版详情
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public TemplateInfoVo getInfoById(Long id) {
        Template template = templateRepository.findById(id).orElseThrow(DataErrorCode.DATA_NOT_FOUND::buildException);
        return BeanUtil.copyProperties(template , TemplateInfoVo.class);
    }

    /**
     * 根据条件查询模版信息
     *
     * @param findTemplateRpageParam 查询条件
     */
    @Override
    public List<TemplateInfoVo> getInfoByParam(FindTemplateRPageParam findTemplateRpageParam) {

        QTemplate qTemplate = QTemplate.template;
        return  jpaQueryFactory.select(Projections.bean(TemplateInfoVo.class,
                        qTemplate.id,
                        qTemplate.templateCode,
                        qTemplate.templateName,
                        qTemplate.templateType,
                        qTemplate.videoUrl,
                        qTemplate.imagesUrl,
                        qTemplate.industryType,
                        qTemplate.isDefault,
                        qTemplate.updateAt,
                        qTemplate.updateBy
                        ))
                .from(qTemplate)
                .where(queryDsl(qTemplate , findTemplateRpageParam))
                .fetch();
    }

    /**
     * 生成 模版code
     */
    private String getCode() {
        // 生成code
        String code = RandomUtil.randomStringUpper(4);
        // 判断code是否存在
        Integer count =templateRepository.countByTemplateCode(code);
        if (count > 0) {
            getCode();
        }
        return code;

    }

    /**
     * 模版查询条件的构建
     */
    private BooleanBuilder  queryDsl (QTemplate template , FindTemplateRPageParam findTemplateRpageParam) {
        return BoolBuilder.getInstance()
                .and(findTemplateRpageParam.getTemplateName() , template.templateName::contains )
                .and(findTemplateRpageParam.getTemplateType() , template.templateType::eq)
                .and(findTemplateRpageParam.getIsDefault() , template.isDefault::eq)
                .and(findTemplateRpageParam.getIndustryType() , template.industryType::eq)
                .and(findTemplateRpageParam.getLockStatus() , template.lockStatus::eq)
                .and(AppConstants.N , template.deleteStatus::eq)
                .getWhere();
    }
}
