package com.yuansaas.user.permission.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotBlank(message = "用户id不能为空")
    private  Long userId;
    /**
     * 角色id
     */
    @NotEmpty(message = "角色id不能为空")
    private List<Long>  roleId;
}
