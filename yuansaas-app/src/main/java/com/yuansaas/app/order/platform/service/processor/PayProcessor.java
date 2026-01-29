package com.yuansaas.app.order.platform.service.processor;

import com.yuansaas.app.order.platform.entity.Order;
import com.yuansaas.app.order.platform.enums.OrderStatusEnum;
import com.yuansaas.app.order.platform.enums.PayStatusEnum;
import com.yuansaas.app.order.platform.model.OrderPayParam;
import com.yuansaas.app.order.platform.repository.OrderRepository;
import com.yuansaas.app.order.platform.service.OrderService;
import com.yuansaas.core.context.AppContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 *
 * 订单支付处理器
 *
 * @author LXZ 2026/1/19 17:25
 */
@Slf4j
@Component("payProcessor")
@RequiredArgsConstructor
public class PayProcessor extends ActionProcessor{

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public <T>Boolean process(T t) {
        OrderPayParam orderPayParam = null;
        if (t instanceof OrderPayParam) {
            orderPayParam = (OrderPayParam)t;
        }
        Order orderByOrderNo = orderService.findOrderByOrderNo(orderPayParam.getOrderNo());
        orderByOrderNo.setOrderStatus(OrderStatusEnum.COMPLETE.getName());
        orderByOrderNo.setTradeNo(orderPayParam.getTradeNo());
        orderByOrderNo.setPayStatus(PayStatusEnum.SUCCESS.getName());
        orderByOrderNo.setPayMethod(orderPayParam.getPayChannel().getName());
        orderByOrderNo.setPayAmount(orderPayParam.getPayAmount());
        orderByOrderNo.setPayTime(orderPayParam.getPaySucceededTime());
        orderByOrderNo.setUpdateAt(LocalDateTime.now());
        orderByOrderNo.setUpdateBy(AppContextUtil.getUserInfo());
        orderRepository.save(orderByOrderNo);

        //todo 账户入账

        return true;
    }
}
