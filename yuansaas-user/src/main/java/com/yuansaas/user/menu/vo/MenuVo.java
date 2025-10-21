package com.yuansaas.user.menu.vo;

import lombok.Data;

/**
 *
 * 菜单列表vo
 *
 * @author LXZ 2025/10/21 11:54
 */
@Data
public class MenuVo {

    /**
     * 菜单code
     */
    private Long   menuCode;
    /**
     * 上级ID，一级菜单为0
     */
    private Long   pid;
    /**
     * 名称
     */
    private String name;
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 菜单图标
     */
    private String  icon;
    /**
     * 排序
     */
    private Integer sort;
}
