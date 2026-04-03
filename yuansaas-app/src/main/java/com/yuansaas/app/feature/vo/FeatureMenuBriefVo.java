package com.yuansaas.app.feature.vo;

import com.yuansaas.user.menu.vo.MenuVo;
import lombok.Data;

import java.util.List;

/**
 *
 * 功能菜单简要信息vo
 *
 * @author LXZ 2026/2/2 11:44
 */
@Data
public class FeatureMenuBriefVo {
    /**
     * 功能code
     */
    private String featureCode;
    /**
     * 功能name
     */
    private String featureName;
    /**
     * 功能描述
     */
    private String description;
    /**
     * 功能类型
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureTypeEnum}
     */
    private String featureType;
    /**
     * 作用范围
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureScopeEnum}
     */
    private String featureScope;

    /**
     * 关联的菜单信息
     */
    private List<MenuVo> menuVos;

}
