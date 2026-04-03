package com.yuansaas.user.permission.params;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 *
 * 分配用户给部门参数
 *
 * @author LXZ 2026/1/27 17:17
 */
@Data
@Builder
public class AssignUserDeptParam {

    /**
     * 商铺code
     */
    private String shopCode;
    /**
     * 用户id
     */
    private  Long userId;
    /**
     * 部门id
     */
    private Long  deptId;
}
