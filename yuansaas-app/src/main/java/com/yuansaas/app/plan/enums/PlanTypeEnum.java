package com.yuansaas.app.plan.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 套餐类型
 *
 * @author LXZ 2026/2/1 15:56
 */
@Getter
@AllArgsConstructor
public enum PlanTypeEnum implements IBaseEnum<PlanTypeEnum> {

    BASIC("基础版"),
    RESERVE("预约版（'美容、美发、体检、摄影...'）"),
    MARKETING("营销版 ('优惠券、拼团、分销....')"),
    SAAS("标准版 ('通用电商功能')"),
    INDUSTRY("行业版 ('餐饮、零售、医疗、教育...')")

    ;

    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
