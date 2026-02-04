package com.yuansaas.app.template.service.impl;

import com.yuansaas.app.template.params.FindTemplateRPageParam;
import com.yuansaas.app.template.params.TemplateCreateParam;
import com.yuansaas.app.template.params.TemplateUpdateParam;
import com.yuansaas.app.template.service.TemplateService;
import org.springframework.stereotype.Service;

/**
 *
 * 模版操作 Service 实现类
 *
 * @author LXZ 2026/2/4 20:30
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    /**
     * 创建模版
     *
     * @param templateCreateParam 模版新增参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean add(TemplateCreateParam templateCreateParam) {
        return null;
    }

    /**
     * 修改模版
     *
     * @param templateUpdateParam 模版修改参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean update(TemplateUpdateParam templateUpdateParam) {
        return null;
    }

    /**
     * 禁用模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean disable(Long id) {
        return null;
    }

    /**
     * 启用模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean enable(Long id) {
        return null;
    }

    /**
     * 删除模版
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean delete(Long id) {
        return null;
    }

    /**
     * 获取模版分页列表
     *
     * @param findTemplateRPageParam 模版
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean getByRPage(FindTemplateRPageParam findTemplateRPageParam) {
        return null;
    }

    /**
     * 获取模版详情
     *
     * @param id 模版id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean getInfoById(Long id) {
        return null;
    }
}
