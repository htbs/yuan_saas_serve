package com.yuansaas.app.common.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 平台类型
 *
 * @author LXZ 2025/11/17 16:16
 */
@Getter
@AllArgsConstructor
public enum DictPlatformTypeEnum implements IBaseEnum<DictPlatformTypeEnum> {

    /**
     * 全平台共用
     */
    COMMON("公共" ),

     /**
     * 总平台端
     */
    PLATFORM("平台" ),

     /**
     * 商家端
     */
     SHOP("商家" ),

    /**
     * 用户端
     */
      USER("用户"),
    ;

    private final String name;

    @Override
    public String getName() {
        return this.name();
    }
}
