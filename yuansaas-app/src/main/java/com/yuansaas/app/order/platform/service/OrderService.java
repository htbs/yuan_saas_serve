package com.yuansaas.app.order.platform.service;

import com.yuansaas.app.order.platform.entity.Order;
import com.yuansaas.app.order.platform.params.FindOrderPageParam;
import com.yuansaas.app.order.platform.vo.OrderItemDataVo;
import com.yuansaas.app.order.platform.vo.OrderPageListVo;
import com.yuansaas.core.page.RPage;

import java.util.List;

/**
 *
 * 订单管理
 *
 * @author LXZ 2026/1/18 15:27
 */
public interface OrderService {
    /**
     * 订单分页查询
     * @param findOrderPageParam  查询订单分页列表参数
     * @return OrderPageListVo
     */
    RPage<OrderPageListVo> findByPage(FindOrderPageParam findOrderPageParam);

    /**
     * 查询订单详情信息
     *
     * @param orderNo 订单号
     * @return OrderVo
     */
    Order findOrderByOrderNo(Long orderNo);


    /**
     * 查询子订单详情信息
     *
     * @param orderNo 订单号
     * @return OrderItemDataVo
     */
     List<OrderItemDataVo> findOrderItemByOrderNo(Long orderNo);

     /**
      * 通过店铺的订单数据
      * @param orderType  订单类型
      * @param shopCode 店铺code
      * @param orderStatus  订单类型
      */
     List<Order> findShopCodeAndOrderType(String shopCode , String orderType , String orderStatus);




}
