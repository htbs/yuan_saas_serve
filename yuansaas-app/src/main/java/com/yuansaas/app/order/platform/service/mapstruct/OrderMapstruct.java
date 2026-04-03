package com.yuansaas.app.order.platform.service.mapstruct;

import com.yuansaas.app.order.platform.entity.Order;
import com.yuansaas.app.order.platform.enums.OrderStatusEnum;
import com.yuansaas.app.order.platform.params.SubmitOrderParam;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.utils.id.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 订单元素映射类
 *
 * @author LXZ 2026/1/18 18:14
 */
@Component
@RequiredArgsConstructor
public class OrderMapstruct {

    private final SnowflakeIdGenerator idGenerator;

    public Order toSave(SubmitOrderParam submitOrderParam){
        Order order = new Order();
        order.setShopCode(submitOrderParam.getShopCode());
        order.setOrderNo(idGenerator.nextId());
        order.setOrderType(submitOrderParam.getOrderType());
        order.setOrderStatus(OrderStatusEnum.WAIT_PAY.getName());
        order.setMerchandiseNames(submitOrderParam.getMerchandiseName());
        order.setDiscountAmount(0L);
        order.setCreateBy(AppContextUtil.getUserInfo());
        order.setCreateAt(LocalDateTime.now());
        return order;
    }

}
