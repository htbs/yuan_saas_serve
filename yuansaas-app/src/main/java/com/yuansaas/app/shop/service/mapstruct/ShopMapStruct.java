package com.yuansaas.app.shop.service.mapstruct;

import com.yuansaas.app.shop.entity.Shop;
import com.yuansaas.app.shop.param.SaveShopParam;
import com.yuansaas.app.shop.param.UpdateShopParam;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContext;
import com.yuansaas.core.context.AppContextUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *
 *
 * @author LXZ 2025/12/12 10:57
 */
@Component
public class ShopMapStruct {



    public Shop toSaveShop (SaveShopParam shopParam) {
        Shop shop = new Shop();
        shop.setName(shopParam.getName());
        shop.setType(shopParam.getType());
//        shop.setProvinceCode();
//        shop.setCityCode();
//        shop.setDistrictCode();
//        shop.setAddress();
        shop.setLegalPersonName(shopParam.getLegalPersonName());
        shop.setLegalPersonPhone(shopParam.getLegalPersonPhone());
        shop.setLegalPersonEmail(shopParam.getLegalPersonEmail());
        shop.setUnifiedCreditCode(shopParam.getUnifiedCreditCode());
        shop.setIdCardFront(shopParam.getIdCardFront());
        shop.setIdCardBack(shopParam.getIdCardBack());
        shop.setBusinessLicense(shopParam.getBusinessLicense());
        shop.setLockStatus(AppConstants.N);
        shop.setDeleteStatus(AppConstants.N);
        shop.setCreateBy(AppContextUtil.getUserInfo());
        shop.setCreateAt(LocalDateTime.now());
        return shop;
    }

    public void toUpdateShop ( Shop shop , UpdateShopParam shopParam) {
        shop.setName(shopParam.getName());
        shop.setType(shopParam.getType());
//        shop.setProvinceCode();
//        shop.setCityCode();
//        shop.setDistrictCode();
//        shop.setAddress();
        shop.setLegalPersonName(shopParam.getLegalPersonName());
        shop.setLegalPersonPhone(shopParam.getLegalPersonPhone());
        shop.setLegalPersonEmail(shopParam.getLegalPersonEmail());
        shop.setUnifiedCreditCode(shopParam.getUnifiedCreditCode());
        shop.setIdCardFront(shopParam.getIdCardFront());
        shop.setIdCardBack(shopParam.getIdCardBack());
        shop.setBusinessLicense(shopParam.getBusinessLicense());
        shop.setLockStatus(AppConstants.N);
        shop.setDeleteStatus(AppConstants.N);
        shop.setCreateBy(AppContextUtil.getUserInfo());
        shop.setCreateAt(LocalDateTime.now());
    }
}
