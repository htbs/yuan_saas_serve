package com.yuansaas.app.feature.params;
import com.yuansaas.core.page.PageModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 查询功能分页参数
 *
 * @author LXZ 2026/2/12 15:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FindFeatureParam extends PageModel {

    /**
     * 功能名字
     */
    private String featureName;
    /**
     * 功能类型  默认类型 DEFAULT
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureTypeEnum}
     */
    private String featureType;
    /**
     * 作用域  默认类型  ALL
     * 枚举 {@link com.yuansaas.app.feature.enums.FeatureScopeEnum}
     */
    private String featureScope;
    /**
     * 行业类型
     * 枚举 {@link com.yuansaas.app.common.enums.IndustryTypeEnum}
     */
    private String industryType;
}
