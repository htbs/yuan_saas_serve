package com.yuansaas.app.feature.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 功能类型
 *
 * @author LXZ 2026/2/1 15:25
 */
@Getter
@AllArgsConstructor
public enum FeatureTypeEnum implements IBaseEnum<FeatureTypeEnum> {

    INIT   ("开店初始化"),
    PACKAGE("买套餐送"),
    BUY    ("单独购买"),
    CUSTOM ("商户自己创建"),

    ;

    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
