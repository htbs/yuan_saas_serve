package com.yuansaas.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 登录终端
 *
 * @author HTB 2026/1/12 13:29
 */
@RequiredArgsConstructor
@Getter
public enum TerminalEnum implements IBaseEnum<TerminalEnum> {

    // 总平台
    YUAN_SHI_WEB(PlatFormEnum.YUAN_SHI, ClientTypeEnum.WEB),

    // 商家端只有 WEB
    MERCHANT_WEB(PlatFormEnum.MERCHANT, ClientTypeEnum.WEB),

    // 用户端 5 种
    CLIENT_H5(PlatFormEnum.CLIENT, ClientTypeEnum.H5),
    CLIENT_ANDROID(PlatFormEnum.CLIENT, ClientTypeEnum.ANDROID),
    CLIENT_IOS(PlatFormEnum.CLIENT, ClientTypeEnum.IOS),
    CLIENT_WEIXIN_MINI_PROGRAM(PlatFormEnum.CLIENT, ClientTypeEnum.WEIXIN_MINI_PROGRAM);

    private final PlatFormEnum platform;
    private final ClientTypeEnum clientType;
    private final String code;
    private final String label;

    TerminalEnum(PlatFormEnum p, ClientTypeEnum c) {
        this.platform = p;
        this.clientType = c;
        this.code = p.getCode() + "_" + c.getValue();
        this.label = p.getLabel() + "-" + c.getValue();
    }

    /** 根据 platform + clientType 反查 Terminal */
    public static TerminalEnum of(PlatFormEnum p, ClientTypeEnum c) {
        for (TerminalEnum t : values()) {
            if (t.platform == p && t.clientType == c) return t;
        }
        return null;
    }

    /**
     * 根据code 获取终端枚举
     * @return
     */
    public static TerminalEnum getByCode(String code) {
        if (code == null) return null;
        return Arrays.stream(TerminalEnum.values()).filter(t -> t.code.equals(code)).findFirst().orElse( null);
    }

    @Override
    public String getName() {
        return this.name();
    }
}
