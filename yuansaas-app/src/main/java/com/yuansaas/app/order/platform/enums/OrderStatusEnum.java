package com.yuansaas.app.order.platform.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 订单状态
 *
 * @author LXZ 2026/1/18 15:12
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements IBaseEnum<OrderStatusEnum> {

    WAIT_PAY(null,"待支付" , new HashSet<OrderAction>(){{add(OrderAction.submit);}}),

    COMPLETE("WAIT_PAY","已完成", new HashSet<OrderAction>() {{add(OrderAction.pay);add(OrderAction.refund);add(OrderAction.rejectRefund);}}),

    REFUND_COMPLETE("COMPLETE","退款成功", new HashSet<OrderAction>(){{add(OrderAction.confirmRefund);}}),

    CANCEL("WAIT_PAY", "已关闭", new HashSet<OrderAction>() {{add(OrderAction.cancel);}}),
    ;
    /**
     * 上一个状态
     */
    private String prevStatus;
    /**
     * 描述
     */
    private final String message;
    /**
     * 当前状态对应的处理器类型
     */
    private Set<OrderAction> orderAction;

    @Override
    public String getName() {
        return this.name();
    }
}
