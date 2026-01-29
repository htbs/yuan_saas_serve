package com.yuansaas.app.order.platform.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.order.platform.entity.Order;
import com.yuansaas.app.order.platform.entity.QOrder;
import com.yuansaas.app.order.platform.entity.QOrderItem;
import com.yuansaas.app.order.platform.params.FindOrderPageParam;
import com.yuansaas.app.order.platform.repository.OrderRepository;
import com.yuansaas.app.order.platform.service.OrderService;
import com.yuansaas.app.order.platform.vo.OrderItemDataVo;
import com.yuansaas.app.order.platform.vo.OrderPageListVo;
import com.yuansaas.app.shop.entity.QShop;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 订单管理实现类
 *
 * @author LXZ 2026/1/18 15:45
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final JPAQueryFactory jpaQueryFactory;
    private final OrderRepository orderRepository;


    /**
     * 订单分页查询
     *
     * @param findOrderPageParam 查询订单分页列表参数
     * @return OrderPageListVo
     */
    @Override
    public RPage<OrderPageListVo> findByPage(FindOrderPageParam findOrderPageParam) {
        QOrder qOrder = QOrder.order;
        QShop qShop = QShop.shop;

        QueryResults<OrderPageListVo> orderPageListVoQueryResults = jpaQueryFactory.select(Projections.bean(OrderPageListVo.class,
                        qOrder.id,
                        qOrder.orderNo,
                        qOrder.orderType,
                        qOrder.merchandiseAmount.as("orderAmount"),
                        qOrder.discountAmount,
                        qOrder.shopCode,
                        qShop.name.as("shopName"),
                        qOrder.createAt,
                        qOrder.createBy,
                        qOrder.orderStatus
                ))
                .from(qOrder)
                .leftJoin(qShop).on(qOrder.shopCode.eq(qShop.code))
                .where(BoolBuilder.getInstance()
                        .and(findOrderPageParam.getOrderType(), qOrder.orderType::eq)
                        .and(findOrderPageParam.getShopCode(), qOrder.shopCode::eq)
                        .and(findOrderPageParam.getShopName(), qShop.name::contains)
                        .getWhere())
                .orderBy(qOrder.createAt.desc())
                .limit(findOrderPageParam.getPageSize())
                .offset(findOrderPageParam.obtainOffset())
                .fetchResults();
        return new RPage<>(findOrderPageParam.getPageNo(), findOrderPageParam.getPageSize(),orderPageListVoQueryResults.getResults() , orderPageListVoQueryResults.getTotal());
    }

    /**
     * 查询订单详情信息
     *
     * @param orderNo 订单号
     * @return OrderVo
     */
    @Override
    public Order findOrderByOrderNo(Long orderNo) {
        Order byOrderNo = orderRepository.findByOrderNo(orderNo);
        if (ObjectUtil.isEmpty(byOrderNo)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException();
        }
        return byOrderNo;
    }

    /**
     * 查询子订单详情信息
     *
     * @param orderNo 订单号
     * @return OrderItemDataVo
     */
    @Override
    public List<OrderItemDataVo> findOrderItemByOrderNo(Long orderNo) {
        QOrderItem qOrderItem = QOrderItem.orderItem;
        List<OrderItemDataVo> orderItemDataVos = jpaQueryFactory.select(Projections.bean(OrderItemDataVo.class,
                        qOrderItem.orderNo,
                        qOrderItem.merchandiseAmount,
                        qOrderItem.type,
                        qOrderItem.merchandiseName,
                        qOrderItem.merchantOrderExt
                ))
                .from(qOrderItem)
                .where(BoolBuilder.getInstance()
                        .and(orderNo, qOrderItem.orderNo::eq)
                        .getWhere())
                .fetch();
        if (ObjectUtil.isEmpty(orderItemDataVos)) {
            return null;
        }
        return orderItemDataVos;
    }

    /**
     * 通过店铺的订单数据
     *
     * @param shopCode    店铺code
     * @param orderType   订单类型
     * @param orderStatus 订单类型
     */
    @Override
    public List<Order> findShopCodeAndOrderType(String shopCode, String orderType, String orderStatus) {
        return orderRepository.findByShopCodeAndOrderTypeAndOrderStatus(shopCode, orderType, orderStatus);
    }
}
