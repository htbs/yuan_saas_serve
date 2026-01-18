package com.yuansaas.app.order.platform.model;

import lombok.Data;

import java.util.List;

/**
 *
 * 订单及子订单明细组装模版
 *
 * @author LXZ 2026/1/18 18:22
 */
@Data
public class OrderAndItemDataModel {




    private List<OrderItemModel>  orderItemModelList;

}
