package com.yuansaas.app.order.platform.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * 功能模版model
 *
 * @author LXZ 2026/1/18 16:42
 */
@Data
public class FunctionTemplateModel implements MerchantOrderExtModel{

    private String type = "FUNCTION_TEMPLATE" ;

    /**
     * 功能code （菜单编号）
     */
    private String functionCode;

    /**
     * 开始时间
     */
    private LocalDateTime startDate;
    /**
     * 结束时间
     */
    private LocalDateTime endDate;
}
