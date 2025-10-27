package com.yuansaas.user.menu.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 菜单缓存枚举
 *
 * @author LXZ 2025/10/17 18:11
 */
@Getter
@AllArgsConstructor
public enum MenuCacheEnum implements IBaseEnum<MenuCacheEnum> {

    MENU_LIST("menu_list", "菜单列表缓存"),
    ;

    private final String key;
    private final String desc;

    @Override
    public String getName() {
        return this.name();
    }
}
