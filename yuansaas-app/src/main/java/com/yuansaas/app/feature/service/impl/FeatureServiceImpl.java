package com.yuansaas.app.feature.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.feature.entity.Feature;
import com.yuansaas.app.feature.entity.FeatureMenuLink;
import com.yuansaas.app.feature.entity.QFeature;
import com.yuansaas.app.feature.entity.QFeatureMenuLink;
import com.yuansaas.app.feature.params.AssignFeatureMenuParam;
import com.yuansaas.app.feature.params.FeatureCreateParam;
import com.yuansaas.app.feature.params.FeatureUpdateParam;
import com.yuansaas.app.feature.params.FindFeatureParam;
import com.yuansaas.app.feature.repository.FeatureMenuLinkRepository;
import com.yuansaas.app.feature.repository.FeatureRepository;
import com.yuansaas.app.feature.service.FeatureService;
import com.yuansaas.app.feature.vo.FeaturePageListVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import com.yuansaas.user.menu.entity.Menu;
import com.yuansaas.user.menu.entity.QMenu;
import com.yuansaas.user.menu.service.MenuService;
import com.yuansaas.user.menu.vo.MenuVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 功能操作实现类
 *
 * @author LXZ 2026/1/29 17:39
 */
@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;
    private final FeatureMenuLinkRepository featureMenuLinkRepository;
    private final MenuService menuService;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 新增功能
     *
     * @param featureCreateParam 新增功能参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean create(@Valid FeatureCreateParam featureCreateParam) {
        Feature feature = BeanUtil.copyProperties(featureCreateParam , Feature.class);
        feature.setFeatureCode(getFeatureCode());
        feature.init();
        featureRepository.save(feature);
        return true;
    }

    /**
     * 编辑功能
     *
     * @param featureUpdateParam 功能编辑参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean update(FeatureUpdateParam featureUpdateParam) {
        featureRepository.findById(featureUpdateParam.getId()).ifPresentOrElse(feature -> {
            feature.setFeatureName(feature.getFeatureName());
            feature.setFeatureType(feature.getFeatureType());
            feature.setFeatureScope(feature.getFeatureScope());
            feature.setIndustryType(feature.getIndustryType());
            feature.setDescription(feature.getDescription());
            feature.update();
            featureRepository.save(feature);
        } , ()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return true;
    }

    /**
     * 获取功能列表 （分页）
     *
     * @param findFeatureParam 功能编辑参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public RPage<FeaturePageListVo> getByPage(FindFeatureParam findFeatureParam) {
        QFeature qFeature = QFeature.feature;
        BooleanBuilder boolBuilder = BoolBuilder.getInstance()
                .and(findFeatureParam.getFeatureName() , qFeature.featureName::contains)
                .and(findFeatureParam.getFeatureScope() , qFeature.featureScope::eq)
                .and(findFeatureParam.getFeatureType() , qFeature.featureType::eq)
                .and(findFeatureParam.getIndustryType() , qFeature.industryType::eq)
                .and(AppConstants.N , qFeature.deleteStatus::eq)
                .getWhere();
        return findFeatureParam.getPage(()->
                 jpaQueryFactory.select(Projections.bean(FeaturePageListVo.class,
                         qFeature.id,
                         qFeature.featureName,
                         qFeature.featureScope,
                         qFeature.featureType,
                         qFeature.featureCode,
                         qFeature.industryType,
                         qFeature.description,
                         qFeature.lockStatus,
                         qFeature.updateBy,
                         qFeature.updateAt
                         ))
                         .from(qFeature)
                         .where(boolBuilder)
                         .orderBy(qFeature.createAt.desc())
                , ()->
                        jpaQueryFactory.select(qFeature.id.countDistinct())
                                .from(qFeature)
                                .where(boolBuilder)
        );
    }

    /**
     * 获取功能code列表
     *
     * @param featureCodes 功能code
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public List<String> getFeatureCodeListByFeatureCodes(List<String> featureCodes) {
        return featureRepository.getFeatureCodeListByFeatureCodes(featureCodes);
    }

    /**
     * 获取功能列表
     *
     * @param featureCodes 功能code
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public List<Feature> getFeatureListByFeatureCodes(List<String> featureCodes) {
        return featureRepository.getFeatureListByFeatureCodes(featureCodes);
    }

    /**
     * 分配菜单给功能点上
     *
     * @param assignFeatureMenuParam 功能编辑参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean assignFeatureMenu(@Valid AssignFeatureMenuParam assignFeatureMenuParam) {
        // 判断 数据是否存在
        Feature feature = featureRepository.findById(assignFeatureMenuParam.getId()).orElseThrow(DataErrorCode.DATA_NOT_FOUND::buildException);
        List<Menu> byList = menuService.getByList(assignFeatureMenuParam.getMenuId(), AppConstants.N);
        if (ObjectUtil.isEmpty(byList)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        }
        // 组装数据
        List<FeatureMenuLink> featureMenuLinkList = new ArrayList<>();
        byList.forEach( f ->{
            FeatureMenuLink featureMenuLink = new FeatureMenuLink();
            featureMenuLink.setFeatureCode(feature.getFeatureCode());
            featureMenuLink.setMenuCode(f.getMenuCode());
            featureMenuLink.setLockStatus(AppConstants.N);
            featureMenuLink.init();
            featureMenuLinkList.add(featureMenuLink);
        });

        featureMenuLinkRepository.saveAll(featureMenuLinkList);
        return true;
    }

    /**
     * 获取功能点下分配的菜单code列表
     *
     * @param featureCode 功能id
     * @param lockStatus 锁定状态
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public List<Long> getMenuCodeListByFeatureCodeAndLockStatus(String featureCode  , String lockStatus) {
        return featureMenuLinkRepository.featIdByFeatureCodeAndLockStatus(featureCode, lockStatus);
    }

    /**
     * 获取功能点下分配的菜单cod列表
     *
     * @param featureCode 功能code
     * @param menuType    菜单类型  0：菜单 1：按钮  不传则全部返回
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Map<String, List<MenuVo>> getMenuListByFeatureCodesAndMenuType(List<String> featureCode, Integer menuType) {

        QFeatureMenuLink qFeatureMenuLink = QFeatureMenuLink.featureMenuLink;
        QMenu qMenu = QMenu.menu;

        Expression<MenuVo> menuExpr =
                Projections.bean(
                        MenuVo.class,
                        qMenu.menuCode,
                        qMenu.name,
                        qMenu.menuType,
                        qMenu.pid,
                        qMenu.url,
                        qMenu.icon,
                        qMenu.sort,
                        qMenu.permissions
                );

        return jpaQueryFactory.select(
                        qFeatureMenuLink.featureCode,
                        menuExpr
                )
                .from(qFeatureMenuLink)
                .leftJoin(qMenu).on(qFeatureMenuLink.menuCode.eq(qMenu.menuCode))
                .where(BoolBuilder.getInstance()
                        .and(featureCode , qFeatureMenuLink.featureCode::in)
                        .and(menuType , qMenu.menuType::eq)
                        .getWhere())
                .fetch()
                .stream()
                .collect(
                        Collectors.groupingBy(
                        tuple -> tuple.get(qFeatureMenuLink.featureCode),
                         Collectors.mapping(tuple -> tuple.get(menuExpr),Collectors.toList())
                        )
                );
    }

    /**
     * 根据行业类型获取功能code
     *
     * @param industryType 行业类型
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public List<Feature> getFeatureCodesByIndustryType(String industryType) {
        return featureRepository.getFeatureCodeListByIndustryType(industryType);
    }


    /**
     * 生成功能code
     */
    public String getFeatureCode() {
        String code = RandomUtil.randomStringUpper(AppConstants.FOUR);
        if (validatedFeatureCodeIsExists(code)) {
            getFeatureCode();
        }
        return code ;
    }

    /**
     * 验证功能code是否存在
     * @param featureCode 功能code
     */
    private boolean validatedFeatureCodeIsExists(String featureCode) {
        return featureRepository.countByFeatureCode(featureCode) > 0;
    }

}
