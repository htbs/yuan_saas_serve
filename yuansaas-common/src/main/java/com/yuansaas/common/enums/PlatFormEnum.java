package com.yuansaas.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台枚举
 *
 * @author HTB 2026/1/12 13:23
 */
@Getter
@AllArgsConstructor
public enum PlatFormEnum implements IBaseEnum<PlatFormEnum>{

    YUAN_SHI("YUAN_SHI", "元识总平台"),
    MERCHANT("MERCHANT", "商家端"),
    CLIENT("CLIENT", "用户端");

    private final String code;
    private final String label;

    @Override
    public String getName() {
        return this.name();
    }

}
