package com.yuansaas.user.role.service;

import com.yuansaas.core.page.RPage;
import com.yuansaas.user.role.entity.Role;
import com.yuansaas.user.role.params.FindRoleParam;
import com.yuansaas.user.role.params.SaveRoleParam;
import com.yuansaas.user.role.params.UpdateRoleParam;
import com.yuansaas.user.role.vo.RoleListVo;
import com.yuansaas.user.role.vo.RoleVo;

import java.util.List;

/**
 *
 * 角色管理实例接口
 *
 * @author LXZ 2025/10/21 10:39
 */
public interface RoleService {
    /**
     * 列表查询
     * @param findRoleParam 查询角色参数
     */
    RPage<RoleListVo> getByPage(FindRoleParam findRoleParam);
    /**
     * 新增角色
     * @param saveRoleParam  保存角色信息参数
     */
    Role save(SaveRoleParam saveRoleParam);
    /**
     * 修改角色
     * @param updateRoleParam  修改角色信息参数
     */
    Boolean update(UpdateRoleParam updateRoleParam);
    /**
     * 删除角色
     * @param id 角色id
     */
    Boolean delete(Long id );
    /**
     * 角色详情
     * @param id 角色id
     */
    RoleVo getById(Long id );
    /**
     * 通过角色id查询角色信息
     * @param Id 角色id列表
     * @return 角色列表
     */
    List<Role> getByIdAll(List<Long> Id);
    /**
     * 判断角色数组中，是否有超级管理员
     *
     * @param ids 角色编号数组
     * @return 是否有超级管理员
     */
    Boolean hasAnySuperAdmin(List<Long> ids);



}
