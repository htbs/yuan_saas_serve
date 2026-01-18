package com.yuansaas.app.order.platform.api;

import com.yuansaas.app.order.platform.params.FindOrderPageParam;
import com.yuansaas.app.order.platform.service.OrderService;
import com.yuansaas.app.order.platform.vo.OrderItemDataVo;
import com.yuansaas.app.order.platform.vo.OrderPageListVo;
import com.yuansaas.core.page.RPage;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 订单信息查询管理api
 *
 * @author LXZ 2026/1/18 14:50
 */
@RestController
@RequestMapping(value = "/get/order")
@RequiredArgsConstructor
public class FindOrderApi {

    private final OrderService orderService;

    /**
     * 订单分页查询
     * @param findOrderPageParam  查询订单分页列表参数
     * @return OrderPageListVo
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<RPage<OrderPageListVo>>> findByPage(FindOrderPageParam findOrderPageParam) {
        return ResponseBuilder.okResponse(orderService.findByPage(findOrderPageParam));
    }


    /**
     * 查询订单详情信息
     * @param orderNo 订单号
     * @return OrderItemDataVo
     */
    @RequestMapping(value = "/item/{orderNo}",method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<List<OrderItemDataVo>>> findOrderItemByOrderNo(@RequestParam(value = "orderNo") Long orderNo) {
        return ResponseBuilder.okResponse(orderService.findOrderItemByOrderNo(orderNo));
    }
}
