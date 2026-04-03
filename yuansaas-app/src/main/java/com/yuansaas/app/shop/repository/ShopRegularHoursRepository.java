package com.yuansaas.app.shop.repository;

import com.yuansaas.app.shop.entity.ShopDataConfig;
import com.yuansaas.app.shop.entity.ShopRegularHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 *
 * @author LXZ 2025/12/12 15:28
 */
public interface ShopRegularHoursRepository extends JpaRepository<ShopRegularHours,Long> {

    List<ShopRegularHours> findByShopCode(String shopCode);

    Integer deleteByShopCode(String shopCode);
}
