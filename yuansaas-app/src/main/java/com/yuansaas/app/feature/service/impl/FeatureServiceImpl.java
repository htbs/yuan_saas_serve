package com.yuansaas.app.feature.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.yuansaas.app.feature.entity.Feature;
import com.yuansaas.app.feature.entity.FeatureMenuLink;
import com.yuansaas.app.feature.params.AssignFeatureMenuParam;
import com.yuansaas.app.feature.params.FeatureCreateParam;
import com.yuansaas.app.feature.params.FeatureUpdateParam;
import com.yuansaas.app.feature.repository.FeatureMenuLinkRepository;
import com.yuansaas.app.feature.repository.FeatureRepository;
import com.yuansaas.app.feature.service.FeatureService;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.user.menu.entity.Menu;
import com.yuansaas.user.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 新增功能
     *
     * @param featureCreateParam 新增功能参数
     * @author lxz 2026/01/29 14:35
     */
    @Override
    public Boolean create(@Valid FeatureCreateParam featureCreateParam) {
        Feature feature = new Feature();
        feature.setFeatureCode(getFeatureCode());
        feature.setFeatureName(feature.getFeatureName());
        feature.setDescription(feature.getDescription());
        feature.setLockStatus(AppConstants.N);
        feature.setLockStatus(AppConstants.N);
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
            feature.setDescription(feature.getDescription());
            feature.update();
            featureRepository.save(feature);
        } , ()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        });
        return null;
    }

    /**
     * 分配菜单给功能点
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
    private Boolean validatedFeatureCodeIsExists(String featureCode) {
        return featureRepository.countByFeatureCode(featureCode) > 0;
    }

}
