package com.yuansaas.app.template.params;

import com.yuansaas.app.template.enums.TemplateTypeEnum;
import com.yuansaas.app.template.model.ImagesUrlModel;
import com.yuansaas.core.annotation.EnumValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

/**
 *
 * 修改模版参数
 *
 * @author LXZ 2026/2/3 18:48
 */
@Data
public class TemplateUpdateParam {

    /**
     * 模版id
     */
    @NotNull(message = "模版id不能为空")
    private Long templateId;
    /**
     * 模版name
     */
    @NotBlank(message = "模版名称不能为空")
    @Size(min = 2 , max = 10 , message = "模版名字长度2~10字符")
    private String templateName;
    /**
     * 模版类型  H5/MINI/APP
     * 枚举 {@link TemplateTypeEnum}
     */
    @EnumValidate(enumClass = TemplateTypeEnum.class , message = "模版类型无效")
    private String templateType;
    /**
     * 预览图
     */
    private List<ImagesUrlModel> imagesUrl;
    /**
     * 预览视频
     */
    private String videoUrl;
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
