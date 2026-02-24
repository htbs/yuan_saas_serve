package com.yuansaas.app.feature.params;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 分配菜单给功能 授权参数
 *
 * @author LXZ 2026/1/29 17:30
 */
@Data
public class AssignFeatureMenuParam implements Serializable {

    /**
     * 功能id
     */
    @NotNull(message = "功能id不能为空")
    private Long id;
    /**
     * 菜单id
     */
    @NotEmpty(message = "菜单id不能为空 ")
    private List<Long> menuId;


}
