package com.yuansaas.app.common.enums;

import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.app.shop.enums.ShopTypeEnum;
import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

/**
 *
 * 行业类型
 *
 * @author LXZ 2026/2/5 18:17
 */
@Getter
@AllArgsConstructor
public enum IndustryTypeEnum implements IBaseEnum<IndustryTypeEnum> {

    APPOINTMENT("预约型 ","美容、美发、体检、摄影...",Set.of(ShopTypeEnum.BEAUTY)),
    E_COMMERCE("电商型 ","卖货、小程序商城...",Set.of()),
    SERVICE("服务型 ","上门维修、保洁...",Set.of()),
    BOOKING("预订型 ","酒店、民宿...",Set.of()),
    COURSE("课程型 ","培训、教育...",Set.of()),
    TICKET("票务型 ","演出、活动..",Set.of()),
    DELIVERY("配送型 ","外卖、同城..",Set.of()),
    STORE_POS("门店收银","线下零售..",Set.of()),
    CONTENT("内容型 ","博主、知识付费...",Set.of()),
    COMMUNITY("社群型 ","社区团购、会员社群...",Set.of()),

    ;
    /**
     * 信息
     */
    private final String message;
    /**
     * 范围描述
     */
    private final String describe;
    /**
     * 商家类型
     */
    private final Set<ShopTypeEnum> shopTypeEnums;


    @Override
    public String getName() {
        return this.name();
    }


    public static IndustryTypeEnum isExists (String shopTypeEnum) {

        ShopTypeEnum shopTypeEnum1 = ShopTypeEnum.valueOf(shopTypeEnum);
        if (ObjectUtil.isEmpty(shopTypeEnum1)) {
            return null;
        }
        return Arrays.stream(IndustryTypeEnum.values()).filter(f -> ObjectUtil.isNotEmpty(f.getShopTypeEnums()) && f.getShopTypeEnums().contains(shopTypeEnum1))
                .findFirst().orElse(null);
    }
}
