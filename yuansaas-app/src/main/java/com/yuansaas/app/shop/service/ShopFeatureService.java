package com.yuansaas.app.shop.service;

import com.yuansaas.app.shop.model.ShopFeatureLinkModel;
import jakarta.validation.Valid;

import java.util.List;

/**
 *
 * 商铺功能关系 Service
 *
 * @author LXZ 2026/2/24 15:39
 */
public interface ShopFeatureService {

    /**
     * 保存商铺和功能点的关系
     * @param shopFeatureLinkModel 参数监护
     * @author LXZ 2026/2/24  15:52
     */
    Boolean saveLink (@Valid ShopFeatureLinkModel shopFeatureLinkModel) ;
    /**
     * 商家code
     * @param shopCode 商铺code
     * @return 返回功能code
     */
    List<String> findFeatureCodeByShopCode(String shopCode);
}
