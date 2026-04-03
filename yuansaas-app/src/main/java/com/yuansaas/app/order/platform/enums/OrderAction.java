package com.yuansaas.app.order.platform.enums;

import com.yuansaas.common.enums.IBaseEnum;
import com.yuansaas.core.exception.ex.DataErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * 处方订单操作枚举
 * @author cqq 2022/4/25 11:34 AM
 */
@Getter
@AllArgsConstructor
public enum OrderAction implements IBaseEnum<OrderAction> {
    submit("提交"),

    pay("支付"),

    cancel("取消"),

    refund("退款"),

    confirmRefund("确认退款"),

    rejectRefund("拒绝退款"),

    ;

    private final String label;


    /**
     * 支持此操作订单所处的状态
     * @return
     */
    public void canOperateOrderStatus(OrderStatusEnum orderStatusEnum) {
        Set<OrderStatusEnum> supportOrderStatus = new HashSet<>();
        if( !supportOrderStatus.contains(orderStatusEnum)){
            throw new RuntimeException(DataErrorCode.DATA_NOT_FOUND.buildException(String.format("%s订单不允许%s",orderStatusEnum.getName(),this.getLabel())));
        }

    }

    @Override
    public String getName() {
        return this.name();
    }
}
