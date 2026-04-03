package com.yuansaas.app.feature.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 作用范围
 *
 * @author LXZ 2026/2/1 15:25
 */
@Getter
@AllArgsConstructor
public enum FeatureScopeEnum implements IBaseEnum<FeatureScopeEnum> {
    ALL("全部"),
    PLATFORM("平台级"),
    MERCHANT("商户级")

    ;

    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
