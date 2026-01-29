package com.yuansaas.user.role.params;

import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.page.PageModel;
import lombok.Data;

/**
 *
 * 角色查询参数
 *
 * @author LXZ 2025/10/21 10:48
 */
@Data
public class FindRoleParam extends PageModel {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 商家编码
     */
    private String shopCode = AppContextUtil.getShopCode();
}
