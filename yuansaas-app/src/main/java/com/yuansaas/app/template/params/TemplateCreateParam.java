package com.yuansaas.app.template.params;

import com.yuansaas.app.template.enums.TemplateTypeEnum;
import com.yuansaas.core.annotation.EnumValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 保存模版参数
 *
 * @author LXZ 2026/2/3 18:48
 */
@Data
public class TemplateCreateParam implements Serializable {

    /**
     * 模版name
     */
    @NotBlank(message = "模版名称不能为空")
    @Size(min = 2 , max = 10 , message = "模版名字长度2~10字符")
    private String templateName;
    /**
     * 模版类型  H5/MINI/APP
     * 枚举 {@link com.yuansaas.app.template.enums.TemplateTypeEnum}
     */
    @EnumValidate(enumClass = TemplateTypeEnum.class , message = "模版类型无效")
    private String templateType;
    /**
     * 套餐code
     */
    @NotBlank(message = "套餐不能为空")
    private String planCode;
    /**
     * 预览图
     */
    @NotBlank(message = "预览图不能为空")
    private String previewUrl;
    /**
     * 封面
     */
    @NotBlank(message = "封面不能为空")
    private String coverImage;
    /**
     * 是否默认 (Y 默认 | N 不默认)
     */
    private String isDefault = "N";
}
