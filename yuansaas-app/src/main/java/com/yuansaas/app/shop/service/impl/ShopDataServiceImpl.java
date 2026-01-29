package com.yuansaas.app.shop.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.yuansaas.app.order.enums.PayChannelEnum;
import com.yuansaas.app.order.platform.entity.Order;
import com.yuansaas.app.order.platform.enums.OrderStatusEnum;
import com.yuansaas.app.order.platform.enums.OrderTypeEnum;
import com.yuansaas.app.order.platform.model.OrderPayParam;
import com.yuansaas.app.order.platform.service.OrderService;
import com.yuansaas.app.order.platform.service.processor.ActionProcessor;
import com.yuansaas.app.shop.entity.Shop;
import com.yuansaas.app.shop.entity.ShopDataConfig;
import com.yuansaas.app.shop.entity.ShopRegularHours;
import com.yuansaas.app.shop.entity.ShopSpecialHours;
import com.yuansaas.app.shop.model.*;
import com.yuansaas.app.shop.param.*;
import com.yuansaas.app.shop.repository.ShopDataConfigRepository;
import com.yuansaas.app.shop.repository.ShopRegularHoursRepository;
import com.yuansaas.app.shop.repository.ShopRepository;
import com.yuansaas.app.shop.repository.ShopSpecialHoursRepository;
import com.yuansaas.app.shop.service.ShopDataService;
import com.yuansaas.app.shop.service.ShopUserService;
import com.yuansaas.app.shop.service.mapstruct.ShopMapStruct;
import com.yuansaas.app.shop.vo.ShopBusinessHoursVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContext;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jackson.JacksonUtil;
import com.yuansaas.core.utils.id.SnowflakeIdGenerator;
import com.yuansaas.user.role.enums.RoleCodeEnum;
import com.yuansaas.user.role.enums.RoleTypeEnum;
import com.yuansaas.user.role.params.SaveRoleParam;
import com.yuansaas.user.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 商家基本配置服务实现类
 *
 * @author LXZ 2025/12/21 18:50
 */
@Service
@RequiredArgsConstructor
public class ShopDataServiceImpl implements ShopDataService {


    private final ShopRepository shopRepository;
    private final ShopDataConfigRepository shopDataConfigRepository;
    private final ShopMapStruct shopMapStruct;
    private final ShopRegularHoursRepository shopRegularHoursRepository;
    private final ShopSpecialHoursRepository shopSpecialHoursRepository;
    private final RoleService roleService;
    private final OrderService orderService;
    private final SnowflakeIdGenerator idGenerator;
    private final ShopUserService shopUserService;

    /**
     * 激活商家
     *
     * @param shopInitModel 激活商铺参数
     *
     * @author lxz 2025/11/16 14:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean init(ShopInitModel shopInitModel) {
        // 初始化店铺基本配置信息
        save(SaveShopDataParam.builder().shopCode(shopInitModel.getShop().getCode()).build());
        // 初始化店铺日常营业时间信息配置
        initRegularHours(shopInitModel.getShop().getCode());
        // 设置初始值角色
        Long roleId = setRoleInit(shopInitModel.getShop().getCode());
        // 设置初始值admin账号
        initUserAccount(shopInitModel.getShop().getCode(),shopInitModel.getShop().getName() , roleId);
        // 修改初始化功能订单状态
        updateInitOrder(shopInitModel.getShop(),shopInitModel.getPayChannel(),shopInitModel.getPayAmount());
        // todo 初始化店铺功能

        return true;
    }

    /**
     * 编辑商家基本信息
     *
     * @param updateShopDataParam 商家参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    @Transactional
    public Boolean update(UpdateShopDataParam updateShopDataParam) {
        shopRepository.findById(updateShopDataParam.getId()).ifPresentOrElse( shop -> {
            // 更新商家信息
            shop.setName(updateShopDataParam.getName());
            shop.setType(updateShopDataParam.getType().name());
            shop.setProvinceCode(updateShopDataParam.getAddress().getProvinceCode());
            shop.setCityCode(updateShopDataParam.getAddress().getCityCode());
            shop.setDistrictCode(updateShopDataParam.getAddress().getDistrictCode());
            shop.setAddress(updateShopDataParam.getAddress());
            shop.setUpdateBy(AppContextUtil.getUserInfo());
            shop.setUpdateAt(LocalDateTime.now());
            shopRepository.save(shop);
            // 更新商家基本配置信息
            ShopDataConfig byShopCode = shopDataConfigRepository.findByShopCode(shop.getCode());
            ShopDataConfig shopDataConfig = null;
            if (ObjectUtil.isEmpty(byShopCode)) {
               shopDataConfig = shopMapStruct.toShopDataConfig(updateShopDataParam, shop);
            }
            shopDataConfigRepository.save(shopDataConfig);
        } ,()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("店铺不存在");
        });
        return true;
    }

    /**
     * 设置商家营业时间
     *
     * @param businessHoursParam 营业时间参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    @Transactional
    public Boolean saveBusinessHours(BusinessHoursParam businessHoursParam) {
        ShopDataConfig shopData = getShopDataConfig(businessHoursParam.getShopCode());
        shopData.setStartTime(businessHoursParam.getStartTime());
        shopData.setEndTime(businessHoursParam.getEndTime());
        // 日常时间配置
        if (ObjectUtil.isNotEmpty(businessHoursParam.getRegularHours())) {
            saveOrUpdateRegularHours(businessHoursParam.getRegularHours() , businessHoursParam.getShopCode());
        }
        // 特殊时间配置
        if (ObjectUtil.isNotEmpty(businessHoursParam.getSpecialHours())) {
            saveOrUpdateSpecialHours(businessHoursParam.getSpecialHours() , businessHoursParam.getShopCode());
        }
        return true;
    }

    /**
     * 查询商家营业时间配置
     *
     * @param shopCode 店铺编码
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public ShopBusinessHoursVo getBusinessHoursByShopCode(String shopCode) {
        ShopDataConfig shopDataConfig = getShopDataConfig(shopCode);
        ShopBusinessHoursVo shopBusinessHoursVo = new ShopBusinessHoursVo();
        shopBusinessHoursVo.setStartTime(shopDataConfig.getStartTime());
        shopBusinessHoursVo.setEndTime(shopDataConfig.getEndTime());
        // 查询日常营业时间
        shopBusinessHoursVo.setRegularHours(getRegularHoursByShopCode(shopCode));
        // 查询特殊营业时间
        shopBusinessHoursVo.setSpecialHours(getSpecialHoursByShopCode(shopCode));
        return shopBusinessHoursVo;
    }

    /**
     * 查询商家营业时间
     *
     * @param date 时间
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public BusinessDataModel getBusinessHoursByShopCode(Date date , String shopCode) {
        // 查询商家日常营业时间
        List<RegularHoursModel> regularHoursByShopCode = getRegularHoursByShopCode(shopCode);
        // 查询商家特殊营业时间
        List<SpecialHoursModel> specialHoursByShopCode = getSpecialHoursByShopCode(shopCode);
        BusinessDataModel businessDataModel = null;
        if (ObjectUtil.isNotEmpty(specialHoursByShopCode)) {
            businessDataModel =  specialHoursByShopCode.stream()
                    .filter(f -> AppConstants.Y.equals(f.getIsOpen()))
                    .filter(f -> DateUtil.isIn(date, DateUtil.beginOfDay(f.getStartDate()), DateUtil.endOfDay(f.getEndDate())))
                    .findFirst()
                    .map( f ->{
                        BusinessDataModel businessData = new BusinessDataModel();
                        businessData.setIsBusiness(AppConstants.Y);
                        businessData.setStartTime(f.getTimeSlots().getStartTime());
                        businessData.setEndTime(f.getTimeSlots().getStartTime());
                        return businessData;
                    }).orElse(null);
        }
        if (ObjectUtil.isNotEmpty(regularHoursByShopCode)) {
            businessDataModel =  regularHoursByShopCode.stream()
                    .filter(f -> AppConstants.Y.equals(f.getIsOpen()))
                    .filter(f -> f.getDayOfWeek() == DateUtil.dayOfWeek(date))
                    .findFirst()
                    .map( f ->{
                        BusinessDataModel businessData = new BusinessDataModel();
                        businessData.setIsBusiness(AppConstants.Y);
                        businessData.setStartTime(f.getTimeSlots().getStartTime());
                        businessData.setEndTime(f.getTimeSlots().getStartTime());
                        return businessData;
                    }).orElse(null);
        }
        return ObjectUtil.isEmpty(businessDataModel) ? new BusinessDataModel() : businessDataModel;
    }


    /**
     * 保存或更新商家特殊营业时间的配置
     */
    private void saveOrUpdateSpecialHours(List<SpecialHoursModel> specialHoursModels , String shopCode) {
        // 先删除原有配置
        shopSpecialHoursRepository.deleteByShopCode(shopCode);
        // 保存新配置
        List<ShopSpecialHours> shopSpecialHoursList = new ArrayList<>();
        specialHoursModels.forEach(s -> {
            ShopSpecialHours shopSpecialHours = new ShopSpecialHours();
            shopSpecialHours.setShopCode(shopCode);
            shopSpecialHours.setTitle(s.getTitle());
            shopSpecialHours.setStartDate(s.getStartDate());
            shopSpecialHours.setEndDate(s.getEndDate());
            shopSpecialHours.setIsOpen(s.getIsOpen());
            shopSpecialHours.setTimeSlots(JacksonUtil.toJson(s.getTimeSlots()));
            shopSpecialHours.setRemark(s.getRemark());
            shopSpecialHoursList.add(shopSpecialHours);
        });
        shopSpecialHoursRepository.saveAll(shopSpecialHoursList);
        // todo 保存到redis缓存
    }
    /**
     * 保存或更新商家日常营业时间的配置
     */
    private void saveOrUpdateRegularHours(List<RegularHoursModel> regularHoursModels, String shopCode) {
        // 先删除原有配置
        shopRegularHoursRepository.deleteByShopCode(shopCode);
        // 保存新配置
        List<ShopRegularHours> shopRegularHoursList = new ArrayList<>();
        regularHoursModels.forEach(r -> {
            ShopRegularHours shopRegularHours = new ShopRegularHours();
            shopRegularHours.setShopCode(shopCode);
            shopRegularHours.setIsOpen(r.getIsOpen());
            shopRegularHours.setDayOfWeek(r.getDayOfWeek());
            shopRegularHours.setTimeSlots(JacksonUtil.toJson(r.getTimeSlots()));
            shopRegularHours.setRemark(r.getRemark());
            shopRegularHoursList.add(shopRegularHours);
        });
        shopRegularHoursRepository.saveAll(shopRegularHoursList);
        // todo 保存到redis缓存
    }

    /**
     * 获取商家基本配置信息
     */
    private ShopDataConfig getShopDataConfig(String shopCode) {
        ShopDataConfig shopData = shopDataConfigRepository.findByShopCode(shopCode);
        if (ObjectUtil.isEmpty(shopData)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException("店铺不存在");
        }
        return shopData;
    }

    /**
     * 获取商家日常营业时间配置
     */
    private List<RegularHoursModel> getRegularHoursByShopCode(String shopCode) {
        // todo 保存到redis缓存

        // 从数据库获取
        List<ShopRegularHours> shopRegularHoursList = shopRegularHoursRepository.findByShopCode(shopCode);
        if (ObjectUtil.isEmpty(shopRegularHoursList)) {
            return null;
        }
        List<RegularHoursModel> regularHoursModels = new ArrayList<>();
        shopRegularHoursList.forEach(r -> {
            RegularHoursModel regularHoursModel = new RegularHoursModel();
            regularHoursModel.setIsOpen(r.getIsOpen());
            regularHoursModel.setDayOfWeek(r.getDayOfWeek());
            regularHoursModel.setTimeSlots(JacksonUtil.convert(JSONUtil.parseObj(r.getTimeSlots()), TimeSlotsModel.class ));
            regularHoursModel.setRemark(r.getRemark());
            regularHoursModels.add(regularHoursModel);
        });
        return regularHoursModels;
    }

    /**
     * 获取商家特殊营业时间配置
     */
    private List<SpecialHoursModel> getSpecialHoursByShopCode(String shopCode) {
        // todo 保存到redis缓存

        // 从数据库获取
        List<ShopSpecialHours> shopSpecialHoursList = shopSpecialHoursRepository.findByShopCode(shopCode);
        if (ObjectUtil.isEmpty(shopSpecialHoursList)) {
            return null;
        }
        List<SpecialHoursModel> specialHoursModels = new ArrayList<>();
        shopSpecialHoursList.forEach(s -> {
            SpecialHoursModel specialHoursModel = new SpecialHoursModel();
            specialHoursModel.setTitle(s.getTitle());
            specialHoursModel.setStartDate(s.getStartDate());
            specialHoursModel.setEndDate(s.getEndDate());
            specialHoursModel.setIsOpen(s.getIsOpen());
            specialHoursModel.setTimeSlots(JacksonUtil.convert(JSONUtil.parseObj(s.getTimeSlots()), TimeSlotsModel.class ));
            specialHoursModel.setRemark(s.getRemark());
            specialHoursModels.add(specialHoursModel);
        });
        return specialHoursModels;
    }

    /**
     * 生产店铺的默认配置信息
     */
    private void save(SaveShopDataParam saveShopDataParam) {
        // 更新商家信息
        ShopDataConfig shopDataConfig = new ShopDataConfig();
        shopDataConfig.setShopCode(saveShopDataParam.getShopCode());
        shopDataConfig.setSubjectColor("#FAFAFA");
        // 可以默认标签
//        shopDataConfig.setLabel();
        shopDataConfig.setIsUnified(AppConstants.Y);
        shopDataConfig.setStartTime(LocalTime.parse("09:00:00"));
        shopDataConfig.setEndTime(LocalTime.parse("18:00:00"));
        shopDataConfig.setCreateBy(AppContextUtil.getUserInfo());
        shopDataConfig.setCreateAt(LocalDateTime.now());
        shopDataConfigRepository.save(shopDataConfig);
    }

    /**
     * 初始化店铺的日常营业时间配置
     */
    private void  initRegularHours (String shopCode) {
        // 保存新配置
        List<ShopRegularHours> shopRegularHoursList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            ShopRegularHours shopRegularHours = new ShopRegularHours();
            shopRegularHours.setShopCode(shopCode);
            shopRegularHours.setIsOpen(AppConstants.N);
            shopRegularHours.setDayOfWeek(i);
            shopRegularHours.setCreateAt(LocalDateTime.now());
            shopRegularHours.setCreateBy(AppContextUtil.getUserInfo());
            shopRegularHoursList.add(shopRegularHours);
        }
        shopRegularHoursRepository.saveAll(shopRegularHoursList);
    }


    /**
     * 设置角色初始值
     * @param shopCode 商铺code
     */
    private Long setRoleInit (String shopCode) {
        SaveRoleParam saveRoleParam = new SaveRoleParam();
        saveRoleParam.setName(RoleCodeEnum.SUPER_ADMIN.getDescribe());
        saveRoleParam.setType(RoleTypeEnum.SYSTEM.getName());
        saveRoleParam.setCode(RoleCodeEnum.SUPER_ADMIN.getCode());
        saveRoleParam.setDescription(RoleCodeEnum.SUPER_ADMIN.getDescribe());
        saveRoleParam.setShopCode(shopCode);
        return roleService.save(saveRoleParam).getId();
    }

    /**
     * 修改初始化功能订单状态
     */
    private void updateInitOrder(Shop shop , PayChannelEnum payChannel , Long payAmount) {
        List<Order> shopCodeAndOrderType = orderService.findShopCodeAndOrderType(shop.getCode(), OrderTypeEnum.INIT_TEMPLATE.getName(), OrderStatusEnum.WAIT_PAY.getName());
        if (ObjectUtils.isEmpty(shopCodeAndOrderType)) {
            return;
        }
        // 修改订单状态
        OrderPayParam orderPayParam = new OrderPayParam();
        orderPayParam.setOrderNo(shopCodeAndOrderType.getFirst().getOrderNo());
        orderPayParam.setTradeNo(String.valueOf(idGenerator.nextId()));
        orderPayParam.setPayAmount(payAmount);
        orderPayParam.setPayChannel(payChannel);
        orderPayParam.setPaySucceededTime(shop.getSignedStartAt().atStartOfDay());
        ActionProcessor.pay(orderPayParam);
    }

    /**
     * 设置初始化用户账号
     */
    private void initUserAccount (String shopCode , String shopName,Long roleId) {
        ShopUserSaveParam shopUserSaveParam = new ShopUserSaveParam();
        shopUserSaveParam.setShopCode(shopCode);
        // 因为是初始化所以账号使用商铺的code
        shopUserSaveParam.setUserName(shopCode);
        shopUserSaveParam.setPassword(AppConstants.PWD);
        shopUserSaveParam.setNickName(shopName);
        shopUserSaveParam.setRoleId(Collections.singletonList(roleId));
        shopUserService.createUser(shopUserSaveParam);
    }

}
