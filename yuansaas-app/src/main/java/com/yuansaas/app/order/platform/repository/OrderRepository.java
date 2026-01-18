package com.yuansaas.app.order.platform.repository;

import com.yuansaas.app.order.platform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * 订单数据库连接操作
 *
 * @author LXZ 2026/1/18 18:57
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
