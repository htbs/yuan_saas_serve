package com.yuansaas.app.shop.service.impl;

import com.yuansaas.app.shop.entity.ShopFeature;
import com.yuansaas.app.shop.model.ShopFeatureLinkModel;
import com.yuansaas.app.shop.repository.ShopFeatureRepository;
import com.yuansaas.app.shop.service.ShopFeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 商铺功能关系 Service
 *
 * @author LXZ 2026/2/24 15:39
 */
@Service
@RequiredArgsConstructor
public class ShopFeatureServiceImpl implements ShopFeatureService {

    private final ShopFeatureRepository shopFeatureRepository;


    @Override
    public Boolean saveLink(@Valid ShopFeatureLinkModel shopFeatureLinkModel) {
        List<ShopFeature> shopFeatureList = new ArrayList<>();
        shopFeatureLinkModel.getFeatureCode().forEach(f ->{
            ShopFeature shopFeature = new ShopFeature();
            shopFeature.setShopCode(shopFeatureLinkModel.getShopCode());
            shopFeature.setFeatureCode(f);
            shopFeature.setExpireTime(shopFeatureLinkModel.getExpireTime());
            shopFeature.setSource(shopFeatureLinkModel.getSource().getName());
            shopFeature.init();
            shopFeatureList.add(shopFeature);
        });
        shopFeatureRepository.saveAll(shopFeatureList);

        // 关联菜单
        return true;
    }

    @Override
    public List<String> findFeatureCodeByShopCode(String shopCode) {
        return shopFeatureRepository.findFeatureCodeByShopCode(shopCode);
    }
}
