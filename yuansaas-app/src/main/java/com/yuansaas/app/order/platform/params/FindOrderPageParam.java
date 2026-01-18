package com.yuansaas.app.order.platform.params;

import com.yuansaas.app.order.platform.enums.OrderTypeEnum;
import com.yuansaas.core.annotation.EnumValidate;
import com.yuansaas.core.page.PageModel;
import lombok.Data;

/**
 *
 * 查询订单分页参数
 *
 * @author LXZ 2026/1/18 14:56
 */
@Data
public class FindOrderPageParam extends PageModel {

    /**
     * 店铺名字
     */
    private String shopName;
    /**
     * 店铺code
     */
    private String shopCode;
    /**
     * 订单类型
     * @see com.yuansaas.app.order.platform.enums.OrderTypeEnum
     */
    @EnumValidate(enumClass = OrderTypeEnum.class , message = "无效的枚举类型")
    private String orderType;
}
