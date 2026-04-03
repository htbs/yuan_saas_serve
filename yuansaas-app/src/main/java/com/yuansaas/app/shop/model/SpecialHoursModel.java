package com.yuansaas.app.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *
 * 特殊日子安排
 *
 * @author LXZ 2026/1/11 16:37
 */
@Data
public class SpecialHoursModel {

    /**
     * 开关 (N 关闭 | Y 打开)
     */
    private String isOpen;
    /**
     * 标题 如：春节、店庆
     */
    private String title;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    /**
     * 时间配置 [{"start":"09:00","end":"18:00"}, ...]
     */
    private TimeSlotsModel timeSlots;
    /**
     * 备注
     */
    private String remark;
}
