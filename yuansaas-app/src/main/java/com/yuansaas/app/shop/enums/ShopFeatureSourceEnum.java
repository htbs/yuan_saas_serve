package com.yuansaas.app.shop.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 商铺功能来源枚举
 *
 * @author LXZ 2026/2/24 15:16
 */
@Getter
@AllArgsConstructor
public enum ShopFeatureSourceEnum implements IBaseEnum<ShopFeatureSourceEnum> {

    INIT("初始化"),
    BUY("购买"),
    ;

    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
