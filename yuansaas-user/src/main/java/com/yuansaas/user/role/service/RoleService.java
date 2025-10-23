package com.yuansaas.user.role.service;

import com.yuansaas.core.page.RPage;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.dept.params.FindDeptParam;
import com.yuansaas.user.dept.params.SaveDeptParam;
import com.yuansaas.user.dept.params.UpdateDeptParam;
import com.yuansaas.user.dept.vo.DeptListVo;
import com.yuansaas.user.dept.vo.DeptTreeListVo;
import com.yuansaas.user.role.params.FindRoleParam;
import com.yuansaas.user.role.params.SaveRoleParam;
import com.yuansaas.user.role.params.UpdateRoleParam;
import com.yuansaas.user.role.vo.RoleListVo;
import com.yuansaas.user.role.vo.RoleVo;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    Boolean save(SaveRoleParam saveRoleParam);
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
     * 角色授权
     * @param id 角色id
     */
    Boolean authorize( Long id );
}
