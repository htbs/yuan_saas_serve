package com.yuansaas.app.order.platform.repository;

import com.yuansaas.app.order.platform.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * 订单子项数据库操作
 *
 * @author LXZ 2026/1/18 18:59
 */
public interface OrderItemRepository extends JpaRepository<OrderItem , Long > {
}
