package com.yuansaas.app.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.order.platform.entity.Order;
import com.yuansaas.app.order.platform.enums.OrderItemTypeEnum;
import com.yuansaas.app.order.platform.enums.OrderStatusEnum;
import com.yuansaas.app.order.platform.enums.OrderTypeEnum;
import com.yuansaas.app.order.platform.model.FunctionTemplateModel;
import com.yuansaas.app.order.platform.model.OrderItemModel;
import com.yuansaas.app.order.platform.model.OrderPayParam;
import com.yuansaas.app.order.platform.params.SubmitOrderParam;
import com.yuansaas.app.order.platform.service.OrderService;
import com.yuansaas.app.order.platform.service.processor.ActionProcessor;
import com.yuansaas.app.shop.entity.QShop;
import com.yuansaas.app.shop.entity.Shop;
import com.yuansaas.app.shop.enums.ShopSignedStatusEnum;
import com.yuansaas.app.shop.enums.ShopTypeEnum;
import com.yuansaas.app.shop.param.*;
import com.yuansaas.app.shop.repository.ShopRepository;
import com.yuansaas.app.shop.service.ShopDataService;
import com.yuansaas.app.shop.service.ShopService;
import com.yuansaas.app.shop.service.mapstruct.ShopMapStruct;
import com.yuansaas.app.shop.vo.ShopListVo;
import com.yuansaas.app.shop.vo.ShopVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import com.yuansaas.core.utils.id.SnowflakeIdGenerator;
import com.yuansaas.user.dept.params.SaveDeptParam;
import com.yuansaas.user.dept.service.DeptService;
import com.yuansaas.user.menu.entity.Menu;
import com.yuansaas.user.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private final JPAQueryFactory jpaQueryFactory;
    private final DeptService deptService;
    private final ShopDataService shopDataService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final SnowflakeIdGenerator idGenerator;



    /**
     * 添加商家
     *
     * @param saveShopParam 商家参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    @Transactional
    public Boolean add(SaveShopParam saveShopParam) {
        // 保存商家并创建部门
        Shop shop = saveShop(saveShopParam);
        // 生成订单
        addOrder(shop);
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
     * 禁用/启用 商家
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
        Shop shop = shopRepository.findById(id).orElse(null);
        if (ObjectUtils.isEmpty(shop)) {
            throw DataErrorCode.DATA_ALREADY_EXISTS.buildException("商家不存在");
        }
        shop.setDeleteStatus(AppConstants.Y);
        shopRepository.save(shop);
        return true;
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
        QShop qShop = QShop.shop;
        QueryResults<ShopListVo> longQueryResults = jpaQueryFactory.select(Projections.bean(
                        ShopListVo.class,
                        qShop.id,
                        qShop.name,
                        qShop.code,
                        qShop.type,
                        qShop.signedStatus,
                        qShop.signedStartAt,
                        qShop.signedEndAt,
                        qShop.createAt,
                        qShop.lockStatus
                ))
                .from(qShop)
                .where(BoolBuilder.getInstance()
                        .and(findShopParam.getCode(), qShop.code::eq)
                        .and(findShopParam.getName(), qShop.name::contains)
                        .and(findShopParam.getSignedStatus(), qShop.signedStatus::eq)
                        .and(AppConstants.N , qShop.deleteStatus::eq)
                        .getWhere())
                .orderBy(qShop.createAt.desc())
                .limit(findShopParam.getPageSize())
                .offset(findShopParam.obtainOffset())
                .fetchResults();

        if (ObjectUtils.isEmpty(longQueryResults)) {
            return new RPage<>(findShopParam.getPageNo(), findShopParam.getPageSize());
        }
        return new RPage<>(findShopParam.getPageNo(), findShopParam.getPageSize(),longQueryResults.getResults(),longQueryResults.getTotal());
    }

    /**
     * 查询商家列表
     *
     * @param id 商家id
     * @return 商家信息
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public ShopVo getById(Long id) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("商家不存在"));
        return BeanUtil.copyProperties(shop, ShopVo.class);
    }

    /**
     * 签约操作
     *
     * @param signedParam 签约参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean signed(SignedParam signedParam) {
        Shop shop = shopRepository.findById(signedParam.getId()).orElseThrow(DataErrorCode.DATA_NOT_FOUND::buildException);
        shop.setSignedStatus(ShopSignedStatusEnum.SIGNED.name());
        shop.setSignedUserId(0L);
        shop.setSignedUserName(signedParam.getName());
        shop.setSignedStartAt(signedParam.getSignedStartAt());
        shop.setSignedEndAt(signedParam.getSignedEndAt());
        shop.setUpdateAt(LocalDateTime.now());
        shop.setUpdateBy(AppContextUtil.getUserInfo());
        shopRepository.save(shop);
        // 签约成功后
        // 默认激活店铺
        shopDataService.init(shop.getCode());
        // 修改功能订单状态
        updateOrder(shop ,signedParam);
        return true;
    }


    /**
     * 生成 商家code
     */
    private String getCode(ShopTypeEnum shopType) {
        // 生成code
        String code =  shopType.name().concat("_").concat(RandomUtil.randomStringUpper(4));
        // 判断code是否存在
        Integer count = shopRepository.countByCode(code);
        if (count > 0) {
            getCode(shopType);
        }
        return code;

    }

    /**
     * 保存商家
     */
    private Shop saveShop(SaveShopParam saveShopParam){
        Shop saveShop = shopMapStruct.toSaveShop(saveShopParam);
        // 生成code
        saveShop.setCode(getCode(saveShopParam.getType()));
        shopRepository.save(saveShop);
        // 生成默认部门id
        SaveDeptParam saveDeptParam = new SaveDeptParam();
        saveDeptParam.setName(saveShop.getName());
        saveDeptParam.setPid(0L);
        saveDeptParam.setShopCode(saveShop.getCode());
        deptService.save(saveDeptParam);
        return saveShop;
    }
    /**
     * 生成功能订单
     */
    private void addOrder(Shop shop){
        // 获取初始化的功能
        List<Menu> wcu3 = menuService.findByMenuCode("WCU3", 0);
        if (ObjectUtils.isEmpty(wcu3)) {
            return;
        }
        List<OrderItemModel> orderItemModelList = new ArrayList<>();
        wcu3.forEach(f ->{
            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setType(OrderItemTypeEnum.FUNCTION_TEMPLATE);
            orderItemModel.setMerchandiseName(f.getName());
            // todo 每个菜单应该有对应的价格
            orderItemModel.setMerchandiseAmount(1);
            FunctionTemplateModel functionTemplateModel = new FunctionTemplateModel();
            functionTemplateModel.setFunctionCode(f.getMenuCode());
            // todo 在签约后给菜单设值开始和结束时间
            orderItemModel.setMerchantOrderExtModel(functionTemplateModel);
            orderItemModelList.add(orderItemModel);
        });

        // todo  创建功能订单
        SubmitOrderParam submitOrderParam = new SubmitOrderParam();
        submitOrderParam.setShopCode(shop.getCode());
        submitOrderParam.setOrderType(OrderTypeEnum.INIT_TEMPLATE.getName());
        submitOrderParam.setMerchandiseName("初始化功能上线");
        submitOrderParam.setOrderItemModelList(orderItemModelList);
        ActionProcessor.submit(submitOrderParam);
    }
    /**
     * 修改初始化功能订单状态
     */
    private void updateOrder(Shop shop , SignedParam signedParam) {
        List<Order> shopCodeAndOrderType = orderService.findShopCodeAndOrderType(shop.getCode(), OrderTypeEnum.INIT_TEMPLATE.getName(), OrderStatusEnum.WAIT_PAY.getName());
        if (ObjectUtils.isEmpty(shopCodeAndOrderType)) {
            return;
        }
        // 修改订单状态
        OrderPayParam orderPayParam = new OrderPayParam();
        orderPayParam.setOrderNo(shopCodeAndOrderType.get(0).getOrderNo());
        orderPayParam.setTradeNo(String.valueOf(idGenerator.nextId()));
        orderPayParam.setPayAmount(signedParam.getPayAmount());
        orderPayParam.setPayChannel(signedParam.getPayChannel());
        orderPayParam.setPaySucceededTime(shop.getSignedStartAt().atStartOfDay());
        ActionProcessor.pay(orderPayParam);
    }

}
