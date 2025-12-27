package com.yuansaas.app.shop.vo;

import com.yuansaas.app.shop.enums.ShopSignedStatusEnum;
import com.yuansaas.app.shop.enums.ShopTypeEnum;
import com.yuansaas.common.model.AddressModel;
import com.yuansaas.core.annotation.EnumValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商家信息VO
 *
 * @author LXZ 2025/12/26 15:32
 */
@Data
public class ShopVo {

    /**
     * 商家ID
     */
    private Long id;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 商家类型
     * @see ShopTypeEnum
     */
    private String type;
    /**
     * 商家地址
     */
    private AddressModel address;
    /**
     * 签约状态
     * @see ShopSignedStatusEnum
     */
    private ShopSignedStatusEnum signedStatus;
    /**
     * 统一社会信用代码
     */
    private String unifiedCreditCode;
    /**
     * 法人姓名
     */
    private String legalPersonName;
    /**
     * 法人性别
     */
    private String legalPersonSex;
    /**
     * 法人手机号
     */
    private String legalPersonPhone;
    /**
     * 法人邮箱
     */
    private String legalPersonEmail;
    /**
     * 法人身份证正面照片
     */
    private String idCardFront;
    /**
     * 法人身份证背面照片
     */
    private String idCardBack;
    /**
     * 营业执照
     */
    private String businessLicense;
}
