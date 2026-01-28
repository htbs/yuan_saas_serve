package com.yuansaas.user.permission.params;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 *
 * 分配角色给用户参数
 *
 * @author LXZ 2026/1/27 17:17
 */
@Data
@Builder
public class AssignUserRoleParam {

    /**
     * 用户id
     */
    private  Long userId;
    /**
     * 角色id
     */
    private List<Long>  roleId;
}
