package com.yuansaas.app.shop.model;

import lombok.Data;

import java.sql.Time;

/**
 *
 * 时间配置model
 *
 * @author LXZ 2026/1/11 16:18
 */
@Data
public class TimeSlotsModel {

    /**
     * 开始时间
     */
    private Time startTime;
    /**
     * 结束时间
     */
    private Time endTime;
    /**
     *  备注
     */
    private String remark;
}
