package com.yuansaas.app.template.service;

import com.yuansaas.app.template.params.FindTemplateRPageParam;
import com.yuansaas.app.template.params.TemplateCreateParam;
import com.yuansaas.app.template.params.TemplateUpdateParam;

/**
 *
 * 模版操作 Service
 *
 * @author LXZ 2026/2/3 17:58
 */
public interface TemplateService {
    /**
     * 创建模版
     * @param templateCreateParam 模版新增参数
     * @author  lxz 2025/11/16 14:35
     */
    Boolean add(TemplateCreateParam templateCreateParam);

    /**
     * 修改模版
     * @param templateUpdateParam 模版修改参数
     * @author  lxz 2025/11/16 14:35
     */
    Boolean update(TemplateUpdateParam templateUpdateParam);

    /**
     * 禁用模版
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    Boolean disable(Long id);

    /**
     * 启用模版
     * @param id 模版id
     * @author  lxz 2025/11/16 14:35
     */
    Boolean enable(Long id);

    /**
     * 删除模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    Boolean delete(Long id);

    /**
     * 获取模版分页列表
     * @param findTemplateRPageParam 模版
     * @author  lxz 2025/11/16 14:35
     */
    Boolean getByRPage(FindTemplateRPageParam findTemplateRPageParam);

    /**
     * 获取模版详情
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    Boolean getInfoById(Long id);
}
