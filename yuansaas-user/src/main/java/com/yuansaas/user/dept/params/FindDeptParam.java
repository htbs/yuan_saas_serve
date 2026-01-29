package com.yuansaas.user.dept.params;

import com.yuansaas.core.context.AppContextUtil;
import lombok.Data;

/**
 *
 * 查询部门信息参数
 *
 * @author LXZ 2025/10/17 11:46
 */
@Data
public class FindDeptParam {
    /**
     * 商家code
     */
    private String shopCode = AppContextUtil.getShopCode();
    /**
     * 部门名称
     */
    private String deptName;
}
