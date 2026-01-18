package com.yuansaas.user.role.params;

import com.yuansaas.core.context.AppContextUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * 角色保存参数
 *
 * @author LXZ 2025/10/21 10:48
 */
@Data
public class SaveRoleParam {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 商家编码
     */
    private String shopCode = AppContextUtil.getMerchantCode();
}
