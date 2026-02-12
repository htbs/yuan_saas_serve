package com.yuansaas.app.template.params;

import com.yuansaas.app.common.enums.IndustryTypeEnum;
import com.yuansaas.app.template.enums.TemplateTypeEnum;
import com.yuansaas.core.annotation.EnumValidate;
import com.yuansaas.core.page.PageModel;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 查询模版分页列表参数
 *
 * @author LXZ 2026/2/3 18:28
 */
@Data
public class FindTemplateRPageParam extends PageModel implements Serializable {
    /**
     * 模版name
     */
    private String templateName;
    /**
     * 模版类型  H5/MINI/APP
     * 枚举 {@link  TemplateTypeEnum}
     */
    @EnumValidate(enumClass = TemplateTypeEnum.class , message = "无效的模版类型")
    private String templateType;
    /**
     * 行业类型
     * 枚举 {@link IndustryTypeEnum}
     */
    @EnumValidate(enumClass = IndustryTypeEnum.class , message = "行业类型无效")
    private String industryType;

    /**
     * 是否默认  （Y 默认 | N 非默认）
     */
    private String isDefault;
    /**
     * 锁定状态 （Y 禁用| N 启用）
     */
    private String lockStatus;
}
