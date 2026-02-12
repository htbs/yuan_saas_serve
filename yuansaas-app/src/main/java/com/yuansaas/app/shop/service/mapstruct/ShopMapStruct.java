package com.yuansaas.app.shop.service.mapstruct;

import com.yuansaas.app.shop.entity.Shop;
import com.yuansaas.app.shop.entity.ShopDataConfig;
import com.yuansaas.app.shop.entity.ShopUser;
import com.yuansaas.app.shop.param.SaveShopParam;
import com.yuansaas.app.shop.param.ShopUserSaveParam;
import com.yuansaas.app.shop.param.UpdateShopDataParam;
import com.yuansaas.app.shop.param.UpdateShopParam;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.jackson.JacksonUtil;
import com.yuansaas.user.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 商铺信息结构映射类
 *
 * @author LXZ 2025/12/12 10:57
 */
@Component
@RequiredArgsConstructor
public class ShopMapStruct {

    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    /**
     * 商铺保存映射方法
     */
    public Shop toSaveShop (SaveShopParam shopParam) {
        Shop shop = new Shop();
        shop.setName(shopParam.getName());
        shop.setType(shopParam.getType().name());
        shop.setProvinceCode(shopParam.getAddress().getProvinceCode());
        shop.setCityCode(shopParam.getAddress().getCityCode()   );
        shop.setDistrictCode(shopParam.getAddress().getDistrictCode());
        shop.setAddress(shopParam.getAddress());
        shop.setLegalPersonName(shopParam.getLegalPersonName());
        shop.setLegalPersonPhone(shopParam.getLegalPersonPhone());
        shop.setLegalPersonEmail(shopParam.getLegalPersonEmail());
        shop.setUnifiedCreditCode(shopParam.getUnifiedCreditCode());
        shop.setLegalPersonSex(shop.getLegalPersonSex());
        shop.setIdCardFront(shopParam.getIdCardFront());
        shop.setIdCardBack(shopParam.getIdCardBack());
        shop.setBusinessLicense(shopParam.getBusinessLicense());
        shop.setSignedStatus(shopParam.getSignedStatus().name());
        shop.setLockStatus(AppConstants.N);
        shop.setDeleteStatus(AppConstants.N);
        shop.setCreateBy(AppContextUtil.getUserInfo());
        shop.setCreateAt(LocalDateTime.now());
        return shop;
    }
    /**
     * 商铺修改参数映射方法
     */
    public void toUpdateShop ( Shop shop , UpdateShopParam shopParam) {
        shop.setName(shopParam.getName());
        shop.setProvinceCode(shopParam.getAddress().getProvinceCode());
        shop.setCityCode(shopParam.getAddress().getCityCode()   );
        shop.setDistrictCode(shopParam.getAddress().getDistrictCode());
        shop.setAddress(shopParam.getAddress());
        shop.setLegalPersonName(shopParam.getLegalPersonName());
        shop.setLegalPersonPhone(shopParam.getLegalPersonPhone());
        shop.setLegalPersonEmail(shopParam.getLegalPersonEmail());
        shop.setUnifiedCreditCode(shopParam.getUnifiedCreditCode());
        shop.setIdCardFront(shopParam.getIdCardFront());
        shop.setIdCardBack(shopParam.getIdCardBack());
        shop.setBusinessLicense(shopParam.getBusinessLicense());
        shop.setSignedStatus(shop.getSignedStatus());
        shop.setLockStatus(AppConstants.N);
        shop.setDeleteStatus(AppConstants.N);
        shop.setCreateBy(AppContextUtil.getUserInfo());
        shop.setCreateAt(LocalDateTime.now());
    }
    /**
     * 商铺基本信息映射方法
     */
    public ShopDataConfig toShopInfoDataConfig(UpdateShopDataParam shopDataParam, Shop shop) {
        ShopDataConfig shopDataConfig = new ShopDataConfig();
        shopDataConfig.setShopCode(shop.getCode());
        shopDataConfig.setIntro(shopDataParam.getIntro());
        shopDataConfig.setLabel(JacksonUtil.toJson(shopDataParam.getLabel()));
        shopDataConfig.setPhone(shopDataParam.getPhone());
        shopDataConfig.setSubjectColor(shopDataParam.getSubjectColor());
        shopDataConfig.setLogo(shopDataParam.getLogo());
        shopDataConfig.setCustomerServiceWechat(shopDataParam.getCustomerServiceWechat());
        shopDataConfig.setOfficialAccounts(shopDataParam.getOfficialAccounts());
        shopDataConfig.setCreateAt(LocalDateTime.now());
        shopDataConfig.setCreateBy(AppContextUtil.getUserInfo());
        shopDataConfig.setUpdateAt(LocalDateTime.now());
        shopDataConfig.setUpdateBy(AppContextUtil.getUserInfo());
        return shopDataConfig;
    }

    /**
     * 商铺用户信息映射方法
     */
    public ShopUser toShopUserSave(ShopUserSaveParam shopUserSaveParam) {
        ShopUser shopUser = new ShopUser();
        shopUser.setShopCode(shopUserSaveParam.getShopCode());
        shopUser.setUserName(shopUserSaveParam.getUserName());
        shopUser.setPassword(passwordEncoder.encode(appProperties.getDefaultPassword()));
        shopUser.setNickName(shopUserSaveParam.getNickName());
        shopUser.setRealName(shopUserSaveParam.getRealName());
        shopUser.setEmail(shopUserSaveParam.getEmail());
        shopUser.setHeadUrl(shopUserSaveParam.getAvatar());
        shopUser.setSex(shopUserSaveParam.getSex());
        shopUser.setPhone(shopUserSaveParam.getPhone());
        return shopUser;
    }
}
