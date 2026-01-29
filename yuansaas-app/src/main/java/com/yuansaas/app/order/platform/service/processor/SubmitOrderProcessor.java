package com.yuansaas.app.order.platform.service.processor;

import cn.hutool.core.util.ObjectUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.order.platform.entity.Order;
import com.yuansaas.app.order.platform.entity.OrderItem;
import com.yuansaas.app.order.platform.enums.OrderTypeEnum;
import com.yuansaas.app.order.platform.model.FunctionTemplateModel;
import com.yuansaas.app.order.platform.model.OrderItemModel;
import com.yuansaas.app.order.platform.params.SubmitOrderParam;
import com.yuansaas.app.order.platform.repository.OrderItemRepository;
import com.yuansaas.app.order.platform.repository.OrderRepository;
import com.yuansaas.app.order.platform.service.mapstruct.OrderMapstruct;
import com.yuansaas.core.context.AppContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 提交订单处理器
 */
@Slf4j
@Component("submitOrderProcessor")
@RequiredArgsConstructor
public class SubmitOrderProcessor extends ActionProcessor {
    private final JPAQueryFactory jpaQueryFactory;
    private final OrderMapstruct orderMapstruct;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <T>Boolean process(T t) {
        SubmitOrderParam submitOrderParam = null;
        if (t instanceof  SubmitOrderParam) {
            submitOrderParam =(SubmitOrderParam) t;
        }
        OrderTypeEnum orderType =OrderTypeEnum.valueOf(submitOrderParam.getOrderType());
        switch (orderType){
            case FUNCTION_AUTH,
                 INIT_TEMPLATE,
                 RENEW_UPGRADE -> {
                verifyFunction(submitOrderParam.getOrderItemModelList());
            }
            case SMS_RECHARGE -> { }
        }
        // 组装订单数据
        Order order = orderMapstruct.toSave(submitOrderParam);
        // 组装子订单数据
        List<OrderItem> orderItems = assembleFunctionData(submitOrderParam, order.getOrderNo());
        if (ObjectUtil.isNotEmpty(orderItems)) {
            Long amount = orderItems.stream().mapToLong(OrderItem::getMerchandiseAmount).sum();
            order.setMerchandiseAmount(amount);
        }
        //保存
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        return true;
    }



    /**
     * 验证功能类型是否存在
     */
    public void verifyFunction(List<OrderItemModel> orderItemModelList){
        List<String> functionCode = new ArrayList<>();
        orderItemModelList.forEach(f -> {
            if (f.getMerchantOrderExtModel() instanceof FunctionTemplateModel) {
                FunctionTemplateModel functionTemplateModel = (FunctionTemplateModel) f.getMerchantOrderExtModel();
                functionCode.add(functionTemplateModel.getFunctionCode());
            }
        });
        // todo 查询菜单code

    }

    /**
     * 组装功能模块数据
     */
    public List<OrderItem>  assembleFunctionData (SubmitOrderParam submitRpOrderParam , Long orderNo) {
        List<OrderItem> orderItems = new ArrayList<>();
        submitRpOrderParam.getOrderItemModelList().forEach(f -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNo(orderNo);
            orderItem.setType(f.getType().getName());
            orderItem.setMerchandiseName(f.getMerchandiseName());
            orderItem.setMerchandiseAmount(f.getMerchandiseAmount());
            orderItem.setMerchantOrderExt(f.getMerchantOrderExtModel());
            orderItem.setCreateBy(AppContextUtil.getUserInfo());
            orderItem.setCreateAt(LocalDateTime.now());
            orderItems.add(orderItem);
        });
        return orderItems;
    }
}
