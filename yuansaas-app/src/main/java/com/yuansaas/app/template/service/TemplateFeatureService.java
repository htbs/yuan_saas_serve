package com.yuansaas.app.template.service;

import com.yuansaas.app.common.enums.IndustryTypeEnum;
import com.yuansaas.app.template.params.AssignTemplateFeatureParam;
import com.yuansaas.app.template.params.FindTemplateFeatureRPageParam;
import com.yuansaas.app.template.vo.TemplateFeaturePageVo;
import com.yuansaas.core.page.RPage;

/**
 *
 * 模版功能关系  - server
 *
 * @author LXZ 2026/2/6 12:07
 */
public interface TemplateFeatureService {

    /**
     * 给模版初始化功能
     * @param templateCode 模版code
     * @param industryType 行业类型
     */
    void init (String templateCode , IndustryTypeEnum industryType);

    /**
     * 分配功能
     *
     * @param assignTemplateFeatureParam 分配参数
     */
    Boolean assignFeature(AssignTemplateFeatureParam assignTemplateFeatureParam);
    /**
     * 移除功能
     *
     * @param assignTemplateFeatureParam 删除参数
     */
    Boolean removeFeature(AssignTemplateFeatureParam assignTemplateFeatureParam);

    /**
     * 禁用 / 启用 操作
     * @param id 关系id
     */
    Boolean lock(Long id);

    /**
     * 获取模版关联的功能
     *
     * @param findTemplateFeatureRPageParam 功能列表查询
     */
     RPage<TemplateFeaturePageVo> getFeatureByTemplateCode(FindTemplateFeatureRPageParam findTemplateFeatureRPageParam);
}
