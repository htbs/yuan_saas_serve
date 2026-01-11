package com.yuansaas.app.shop.vo;

import com.yuansaas.app.shop.model.RegularHoursModel;
import com.yuansaas.app.shop.model.SpecialHoursModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Time;
import java.util.List;

/**
 *
 * 店铺的营业时间配置
 *
 * @author LXZ 2026/1/11 17:32
 */
@Data
public class ShopBusinessHoursVo {
    /**
     *  开始时间 (格式：HH:mm)
     */
    private Time startTime;
    /**
     *  结束时间 (格式：HH:mm)
     */
    private Time endTime;
    /**
     * 日常时间配置
     */
    private List<RegularHoursModel> regularHours;
    /**
     *  特殊时间配置
     */
    private List<SpecialHoursModel> specialHours;
}
