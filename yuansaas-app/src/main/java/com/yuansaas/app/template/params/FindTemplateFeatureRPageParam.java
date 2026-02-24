package com.yuansaas.app.template.params;

import com.yuansaas.core.page.PageModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * 模版关联的功能列表的分页查询
 *
 * @author LXZ 2026/2/9 17:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class FindTemplateFeatureRPageParam extends PageModel {

    /**
     * 模版code
     */
    @NotBlank(message = "模版code不能为空！")
    private String templateCode;
    /**
     * 模版集合
     */
    private List<String> templateCodes;
    /**
     * 功能名字
     */
    private String featureName;
    /**
     * 操作开关 （禁用 Y |启用 N ）
     */
    private String lockStatus;


}
