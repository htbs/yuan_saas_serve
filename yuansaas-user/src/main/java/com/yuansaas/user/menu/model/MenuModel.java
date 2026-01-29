package com.yuansaas.user.menu.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author LXZ 2025/10/23 20:23
 */
@Data
public class MenuModel {

    /**
     * 菜单父ID
     */
    private Long pid;
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 授权
     */
    private List<String> permissionCodes;
    /**
     * 菜单类型 0：菜单 1：按钮
     */
    private Integer menuType;
}
