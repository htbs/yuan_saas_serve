package com.yuansaas.app.order.platform.repository;

import com.yuansaas.app.order.platform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * 订单数据库连接操作
 *
 * @author LXZ 2026/1/18 18:57
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * 通过订单号查询订单
     */
    Order findByOrderNo(Long orderNo);
    /**
     * 查询店铺的订单数据
     */
    List<Order> findByShopCodeAndOrderTypeAndOrderStatus(String shopCode , String orderType , String orderStatus);
}
