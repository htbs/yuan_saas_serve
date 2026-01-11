package com.yuansaas.app.shop.param;

import com.yuansaas.app.shop.model.RegularHoursModel;
import com.yuansaas.app.shop.model.SpecialHoursModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.sql.Time;
import java.util.List;

/**
 *
 * 营业时间设置参数
 *
 * @author LXZ 2026/1/11 16:34
 */
@Data
public class BusinessHoursParam {

    /**
     *  店铺编号
     */
    @NotBlank(message = "店铺编号不能为空")
    private String  shopCode;
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
