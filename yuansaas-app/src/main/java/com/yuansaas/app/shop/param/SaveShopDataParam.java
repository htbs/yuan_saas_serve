package com.yuansaas.app.shop.param;

import com.yuansaas.app.shop.enums.ShopTypeEnum;
import com.yuansaas.common.model.AddressModel;
import com.yuansaas.core.annotation.EnumValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * 修改商家基本信息
 *
 * @author LXZ 2025/12/21 18:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveShopDataParam {

    /**
     * 店铺名称
     */
    private String shopCode;
    /**
     * 店铺logo
     */
    private String logo;
    /**
     * '店铺简介'
     */
    private String  intro;
    /**
     * 手机号
     */
    private String  phone;
    /**
     * 主题色
     */
    private String subjectColor;
    /**
     * '标签'
     */
    private List<String> label;
    /**
     * 客服微信
     */
    private String customerServiceWechat;
    /**
     * 公众号
     */
    private String officialAccounts;



}
