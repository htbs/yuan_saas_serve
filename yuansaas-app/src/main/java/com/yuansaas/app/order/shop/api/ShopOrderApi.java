package com.yuansaas.app.order.shop.api;

import com.yuansaas.app.order.shop.vo.ShopOrderCountVo;
import com.yuansaas.app.shop.param.SaveShopParam;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 商家订单管理api
 *
 * @author LXZ 2026/1/22 17:07
 */
@RequestMapping("/shop")
@RestController
@RequiredArgsConstructor
public class ShopOrderApi {

    /**
     * 查询商家订单总量
     * @param shopCode 商家code
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<ShopOrderCountVo>> add(@RequestParam String shopCode) {
        return ResponseBuilder.okResponse(null);
    }

}
