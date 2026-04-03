package com.yuansaas.app.shop.repository;

import com.yuansaas.app.shop.entity.ShopRegularHours;
import com.yuansaas.app.shop.entity.ShopSpecialHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 *
 * @author LXZ 2025/12/12 15:28
 */
public interface ShopSpecialHoursRepository extends JpaRepository<ShopSpecialHours,Long> {
    List<ShopSpecialHours> findByShopCode(String shopCode);

    Integer deleteByShopCode(String shopCode);
}
