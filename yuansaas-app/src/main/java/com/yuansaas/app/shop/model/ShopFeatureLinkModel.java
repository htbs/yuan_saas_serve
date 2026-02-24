package com.yuansaas.app.shop.model;

import com.yuansaas.app.shop.enums.ShopFeatureSourceEnum;
import com.yuansaas.core.annotation.EnumValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * 商铺功能关系model
 *
 * @author LXZ 2026/2/24 15:46
 */
@Data
public class ShopFeatureLinkModel {

    /**
     * 商铺code
     */
    @NotBlank(message = "商铺code不能为空")
    private String shopCode;
    /**
     * 功能code
     */
    @NotEmpty(message = "商铺code不能为空")
    private List<String> featureCode;
    /**
     * 到期时间
     */
    private LocalDateTime expireTime;
    /**
     * 来源
     */
    @EnumValidate(enumClass = ShopFeatureSourceEnum.class , message = "数据来源枚举无效")
    private ShopFeatureSourceEnum source;
}
