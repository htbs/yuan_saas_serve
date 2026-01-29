package com.yuansaas.app.common.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * Redis缓存枚举
 *
 * @author LXZ 2025/11/19 17:25
 */
@Getter
@RequiredArgsConstructor
public enum CacheEnum implements IBaseEnum<CacheEnum> {

    DICT("DICT:VALUE", "字典缓存"),


    AREA("AREA" , "地区缓存")

    ;

    private final String key;
    private final String desc;

    @Override
    public String getName() {
        return this.name();
    }
}
