package com.yuansaas.user.config;

import com.yuansaas.user.permission.service.PermissionService;
import com.yuansaas.user.users.service.ShopUserService;
import com.yuansaas.user.users.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *  服务管理器 （解决依赖关系）
 * @author LXZ 2025/10/21 17:08
 */
@Component
public class ServiceManager {


    public static SysUserService sysUserService;
    public static PermissionService permissionService;
    public static ShopUserService shopUserService;



    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        ServiceManager.sysUserService = sysUserService;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        ServiceManager.permissionService = permissionService;
    }

    @Autowired
    public void setShopUserService(ShopUserService shopUserService) {ServiceManager.shopUserService = shopUserService;}

}
