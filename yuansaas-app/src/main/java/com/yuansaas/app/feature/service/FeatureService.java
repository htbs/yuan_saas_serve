package com.yuansaas.app.feature.service;

import com.yuansaas.app.feature.entity.Feature;
import com.yuansaas.app.feature.params.AssignFeatureMenuParam;
import com.yuansaas.app.feature.params.FeatureCreateParam;
import com.yuansaas.app.feature.params.FeatureUpdateParam;
import com.yuansaas.user.menu.vo.MenuVo;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 *
 * 功能操作 Service
 *
 * @author LXZ 2026/1/29 17:28
 */
public interface FeatureService {
    /**
     * 新增功能
     * @param featureCreateParam 新增功能参数
     * @author  lxz 2026/01/29 14:35
     */
    Boolean create(@Valid FeatureCreateParam featureCreateParam);
    /**
     * 编辑功能
     * @param featureUpdateParam 功能编辑参数
     * @author  lxz 2026/01/29 14:35
     */
    Boolean update(@Valid FeatureUpdateParam featureUpdateParam);

    /**
     * 获取功能code列表
     *
     * @param featureCodes 功能code
     * @author lxz 2026/01/29 14:35
     */
    List<String> getFeatureCodeListByFeatureCodes(List<String> featureCodes);

    /**
     * 获取功能列表
     *
     * @param featureCodes 功能code
     * @author lxz 2026/01/29 14:35
     */
    List<Feature> getFeatureListByFeatureCodes(List<String> featureCodes);


    /**
     * 分配菜单给功能点
     *
     * @param assignFeatureMenuParam 功能编辑参数
     * @author lxz 2026/01/29 14:35
     */
    Boolean assignFeatureMenu(@Valid AssignFeatureMenuParam assignFeatureMenuParam);

    /**
     * 获取功能点下分配的菜单cod列表
     *
     * @param featureCode 功能id
     * @param lockStatus 锁定状态
     * @author lxz 2026/01/29 14:35
     */
    List<Long> getMenuCodeListByFeatureCodeAndLockStatus(String featureCode,String lockStatus);

    /**
     * 获取功能点下分配的菜单cod列表
     *
     * @param featureCode 功能code
     * @param menuType    菜单类型  0：菜单 1：按钮  不传则全部返回
     * @author lxz 2026/01/29 14:35
     */
    Map<String , List<MenuVo>> getMenuListByFeatureCodesAndMenuType(List<String> featureCode  , Integer menuType);


    /**
     * 根据行业类型获取功能code
     *
     * @param industryType 行业类型
     * @author lxz 2026/01/29 14:35
     */
    List<Feature> getFeatureCodesByIndustryType(String industryType);
}
