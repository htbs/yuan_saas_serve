package com.yuansaas.app.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

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
    @JsonFormat(pattern = "HH:mm:ss" )
    private LocalTime startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
    /**
     *  备注
     */
    private String remark;
}
