package com.yuansaas.app.shop.service;

import com.yuansaas.app.shop.param.*;
import com.yuansaas.app.shop.vo.ShopBusinessHoursVo;

/**
 *
 * 商家实现类
 *
 * @author LXZ 2025/12/12 10:52
 */
public interface ShopDataService {

    /**
     * 编辑店铺基本信息
     *
     * @param shopCode 店铺编码
     * @author lxz 2025/11/16 14:35
     */
    Boolean init(String shopCode);

    /**
     * 编辑店铺基本信息
     * @param updateShopDataParam 店铺参数
     * @author  lxz 2025/11/16 14:35
     */
    Boolean update(UpdateShopDataParam updateShopDataParam);

    /**
     * 设置商家营业时间
     * @param businessHoursParam 营业时间参数
     * @author  lxz 2025/11/16 14:35
     */
    Boolean saveBusinessHours(BusinessHoursParam businessHoursParam);

    /**
     * 查询商家营业时间配置
     * @param shopCode 店铺编码
     * @author  lxz 2025/11/16 14:35
     */
    ShopBusinessHoursVo getBusinessHoursByShopCode(String shopCode);

}
