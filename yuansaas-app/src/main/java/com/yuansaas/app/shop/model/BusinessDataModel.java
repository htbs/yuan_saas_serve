package com.yuansaas.app.shop.model;

import lombok.Data;

import java.time.LocalTime;

/**
 *
 * 营业时间查询
 *
 * @author LXZ 2026/1/12 20:40
 */
@Data
public class BusinessDataModel {
    /**
     * 是否营业
     */
    private String isBusiness = "N";
    /**
     * 营业开始时间
     */
    private LocalTime startTime;
    /**
     * 营业结束时间
     */
    private LocalTime endTime;
}
