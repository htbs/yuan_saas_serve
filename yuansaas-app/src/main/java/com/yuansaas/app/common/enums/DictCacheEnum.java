package com.yuansaas.app.common.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * 字典缓存枚举
 *
 * @author LXZ 2025/11/19 17:25
 */
@Getter
@RequiredArgsConstructor
public enum DictCacheEnum implements IBaseEnum<DictCacheEnum> {

    DICT("DICT:VALUE", "字典缓存"),

    ;

    private final String key;
    private final String desc;

    @Override
    public String getName() {
        return this.name();
    }
}
