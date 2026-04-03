package com.yuansaas.app.template.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 分配功能给模版参数
 *
 * @author LXZ 2026/1/27 17:17
 */
@Data
public class AssignTemplateFeatureParam implements Serializable {

    /**
     * 模版code
     */
    @NotBlank(message = "模版code不能为空！")
    private String templateCode;
    /**
     * 功能code
     */
    @NotEmpty(message = "功能code不能为空！")
    private List<String> featureCodes;
}
