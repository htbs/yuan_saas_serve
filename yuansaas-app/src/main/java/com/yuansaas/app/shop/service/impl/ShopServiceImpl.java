package com.yuansaas.app.shop.service.impl;

import com.yuansaas.app.shop.entity.Shop;
import com.yuansaas.app.shop.param.FindShopParam;
import com.yuansaas.app.shop.param.SaveShopParam;
import com.yuansaas.app.shop.param.UpdateShopParam;
import com.yuansaas.app.shop.repository.ShopRepository;
import com.yuansaas.app.shop.service.ShopService;
import com.yuansaas.app.shop.service.mapstruct.ShopMapStruct;
import com.yuansaas.app.shop.vo.ShopListVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.page.RPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 *
 * 商家实现类
 *
 * @author LXZ 2025/12/12 10:55
 */
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopMapStruct shopMapStruct;
    private final ShopRepository shopRepository;

    /**
     * 添加商家
     *
     * @param saveShopParam 商家参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean add(SaveShopParam saveShopParam) {
        Shop saveShop = shopMapStruct.toSaveShop(saveShopParam);
        // 生成code
//        saveShop.setCode();
        shopRepository.save(saveShop);
        return true;
    }

    /**
     * 编辑商家
     *
     * @param updateShopParam 商家参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean update(UpdateShopParam updateShopParam) {
        Shop shop = shopRepository.findById(updateShopParam.getId()).orElse(null);
        if (ObjectUtils.isEmpty(shop)) {
            throw DataErrorCode.DATA_ALREADY_EXISTS.buildException("商家不存在");
        }
        shopMapStruct.toUpdateShop(shop,updateShopParam);
        shopRepository.save(shop);
        return true;
    }

    /**
     * 禁用商家
     *
     * @param id 商家id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean lock(Long id) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if (ObjectUtils.isEmpty(shop)) {
            throw DataErrorCode.DATA_ALREADY_EXISTS.buildException("商家不存在");
        }
        shop.setLockStatus(AppConstants.N.equals(shop.getLockStatus()) ? AppConstants.Y : AppConstants.N);
        shop.setUpdateAt(LocalDateTime.now());
        shop.setUpdateBy(AppContextUtil.getUserInfo());
        shopRepository.save(shop);
        return true;
    }

    /**
     * 删除商家
     *
     * @param id 商家id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean delete(Long id) {
        return null;
    }

    /**
     * 查询商家列表
     *
     * @param findShopParam 查询参数
     * @return 商家列表
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public RPage<ShopListVo> getByPage(FindShopParam findShopParam) {
        return null;
    }
}
