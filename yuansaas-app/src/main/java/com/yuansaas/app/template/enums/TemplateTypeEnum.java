package com.yuansaas.app.template.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 模版类型
 *
 * @author LXZ 2026/2/3 18:31
 */
@Getter
@AllArgsConstructor
public enum TemplateTypeEnum implements IBaseEnum<TemplateTypeEnum> {

    H5("h5使用") ,
    MINI("小程序使用") ,
    APP("app使用")

    ;

    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
