package com.yuansaas.app.shop.repository;

import com.yuansaas.app.shop.entity.ShopFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * 商铺功能数据库操作
 *
 * @author LXZ 2026/2/24 15:28
 */
public interface ShopFeatureRepository extends JpaRepository<ShopFeature , Long> {

    List<String> findFeatureCodeByShopCode(String shopCode);
}
