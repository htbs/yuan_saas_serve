package com.yuansaas.app.shop.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 商家签约类型
 *
 * @author LXZ 2025/12/15 16:43
 */
@Getter
@AllArgsConstructor
public enum ShopSignedStatusEnum implements IBaseEnum<ShopSignedStatusEnum> {

    /**
     * 未签约
     */
    UNSIGNED("未签约"),
    /**
     * 已签约
     */
    SIGNED("已签约"),
    /**
     * 已过期
     */
    EXPIRED("已过期")

    ;

    private final  String name;

}
