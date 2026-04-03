package com.yuansaas.app.shop.model;

import lombok.Data;

/**
 *
 * 日常时间配置
 *
 * @author LXZ 2026/1/11 16:36
 */
@Data
public class RegularHoursModel {

    /**
     * 1-7: 周一到周日  1表示周日，2表示周一
     */
    private Integer dayOfWeek;
    /**
     * 开关
     */
    private String isOpen;
    /**
     * 时间配置 [{"start":"09:00","end":"18:00"}, ...]
     */
    private TimeSlotsModel timeSlots;
    /**
     * 备注
     */
    private String remark;
}
