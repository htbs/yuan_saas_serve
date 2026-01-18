package com.yuansaas.app.order.platform.params;

import com.yuansaas.app.order.platform.enums.OrderTypeEnum;
import com.yuansaas.app.order.platform.model.OrderItemModel;
import com.yuansaas.core.annotation.EnumValidate;
import lombok.Data;

import java.util.List;

/**
 *
 * 提交订单参数
 *
 * @author LXZ 2026/1/18 17:12
 */
@Data
public class SubmitOrderParam {

    /**
     * 商家code
     */
    private String shopCode;
    /**
     * 订单类型
     * @see com.yuansaas.app.order.platform.enums.OrderTypeEnum
     */
    @EnumValidate(enumClass = OrderTypeEnum.class , message = "无效的枚举类型")
    private String orderType;
    /**
     * 商品名字
     */
    private String merchandiseName;
    /**
     * 明细
     */
    private List<OrderItemModel> orderItemModelList;
}
