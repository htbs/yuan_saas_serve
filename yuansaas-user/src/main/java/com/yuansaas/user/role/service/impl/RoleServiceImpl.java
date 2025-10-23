package com.yuansaas.user.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import com.yuansaas.user.dept.entity.QSysDept;
import com.yuansaas.user.dept.service.DeptService;
import com.yuansaas.user.dept.vo.DeptListVo;
import com.yuansaas.user.role.entity.QRole;
import com.yuansaas.user.role.entity.Role;
import com.yuansaas.user.role.params.FindRoleParam;
import com.yuansaas.user.role.params.SaveRoleParam;
import com.yuansaas.user.role.params.UpdateRoleParam;
import com.yuansaas.user.role.repository.RoleRepository;
import com.yuansaas.user.role.service.RoleService;
import com.yuansaas.user.role.vo.RoleListVo;
import com.yuansaas.user.role.vo.RoleVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 *
 * @author LXZ 2025/10/21 10:47
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final DeptService deptService;

    /**
     * 列表查询
     *
     * @param findRoleParam 查询参数
     */
    @Override
    public RPage<RoleListVo> getByPage(FindRoleParam findRoleParam) {

        QRole role = QRole.role;
        QSysDept dept = QSysDept.sysDept;

        QueryResults<RoleListVo> deptName = jpaQueryFactory.select(Projections.bean(RoleListVo.class,
                        role.id,
                        role.name,
                        role.description,
                        role.deptId,
                        role.createAt,
                        role.createBy,
                        dept.name.as("deptName")
                ))
                .from(role)
                .leftJoin(dept).on(role.deptId.eq(dept.id))
                .where(BoolBuilder.getInstance().getWhere())
                .orderBy(role.id.desc())
                .offset(findRoleParam.obtainOffset())
                .limit(findRoleParam.getPageSize())
                .fetchResults();
        return findRoleParam.getRPage(deptName.getResults() , deptName.getTotal());
    }

    /**
     * 新增角色
     *
     * @param saveRoleParam 新增参数
     */
    @Override
    public Boolean save(SaveRoleParam saveRoleParam) {
        validated(saveRoleParam.getMerchantCode(),saveRoleParam.getDeptId());
        Role role = new Role();
        BeanUtil.copyProperties(saveRoleParam, role);
        role.setCreateAt(LocalDateTime.now());
        role.setCreateBy("admin");
        roleRepository.save(role);
        return true;
    }

    /**
     * 修改角色
     *
     * @param updateRoleParam 修改参数
     */
    @Override
    public Boolean update(UpdateRoleParam updateRoleParam) {
        roleRepository.findById(updateRoleParam.getId()).ifPresentOrElse(role ->{
            validated(role.getMerchantCode(),updateRoleParam.getDeptId());
            role.setName(updateRoleParam.getName());
            role.setDeptId(updateRoleParam.getDeptId());
            role.setDescription(updateRoleParam.getDescription());
            role.setUpdateAt(LocalDateTime.now());
            role.setUpdateBy("admin");
            roleRepository.save(role);
        } , () ->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("角色不存在");
        });
        return true;
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    public Boolean delete(Long id) {
        roleRepository.deleteById(id);
        return true;
    }

    /**
     * 角色详情
     *
     * @param id
     */
    @Override
    public RoleVo getById(Long id) {
      return  roleRepository.findById(id).map(role ->{
            RoleVo roleVo = new RoleVo();
            BeanUtil.copyProperties(role, roleVo);
            return roleVo;
        }).orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("角色不存在"));
    }

    /**
     * 角色授权
     *
     * @param id
     */
    @Override
    public Boolean authorize(Long id) {
        return null;
    }

    /**
     * 校验 角色 相关信息
     */
    public void validated ( String merchantCode , Long deptId ) {
        deptService.getById(merchantCode, deptId);
    }
}
