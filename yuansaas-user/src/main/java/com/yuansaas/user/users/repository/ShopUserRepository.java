package com.yuansaas.user.users.repository;

import com.yuansaas.user.users.entity.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * 商铺用户表 - 数据库操作
 *
 * @author LXZ 2025/12/12 15:28
 */
public interface ShopUserRepository extends JpaRepository<ShopUser,Long> {
    /**
     * 根据商铺code获取商铺信息
     */
    @Query(value = "select post_code from shop_user where shop_code =:shopCode  order by create_at desc LIMIT 1" , nativeQuery = true)
    String findLatestPostCodeByShopCode(String shopCode);

    /**
     * 根据用户名字查询商铺
     */
    Optional<ShopUser> findByUserName(String userName);
}
