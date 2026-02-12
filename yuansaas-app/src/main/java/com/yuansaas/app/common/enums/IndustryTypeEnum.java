package com.yuansaas.app.common.enums;

import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 行业类型
 *
 * @author LXZ 2026/2/5 18:17
 */
@Getter
@AllArgsConstructor
public enum IndustryTypeEnum implements IBaseEnum<IndustryTypeEnum> {

    APPOINTMENT("预约型 ","美容、美发、体检、摄影..."),
    E_COMMERCE("电商型 ","卖货、小程序商城..."),
    SERVICE("服务型 ","上门维修、保洁..."),
    BOOKING("预订型 ","酒店、民宿..."),
    COURSE("课程型 ","培训、教育..."),
    TICKET("票务型 ","演出、活动.."),
    DELIVERY("配送型 ","外卖、同城.."),
    STORE_POS("门店收银","线下零售.."),
    CONTENT("内容型 ","博主、知识付费..."),
    COMMUNITY("社群型 ","社区团购、会员社群..."),

    ;
    /**
     * 信息
     */
    private final String message;
    /**
     * 范围描述
     */
    private final String describe;


    @Override
    public String getName() {
        return this.name();
    }
}
