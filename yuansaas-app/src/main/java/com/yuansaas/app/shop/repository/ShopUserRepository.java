package com.yuansaas.app.shop.repository;

import com.yuansaas.app.shop.entity.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 商铺用户表 - 数据库操作
 *
 * @author LXZ 2025/12/12 15:28
 */
public interface ShopUserRepository extends JpaRepository<ShopUser,Long> {

    @Query(value = "select post_code from shop_user where shop_code =:shopCode  order by create_at desc LIMIT 1" , nativeQuery = true)
    String findLatestPostCodeByShopCode(String shopCode);
}
