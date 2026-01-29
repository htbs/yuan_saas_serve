package com.yuansaas.user.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.BizErrorCode;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.model.BaseEntity;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import com.yuansaas.user.dept.entity.QSysDept;
import com.yuansaas.user.dept.entity.SysDept;
import com.yuansaas.user.dept.service.DeptService;
import com.yuansaas.user.menu.service.MenuService;
import com.yuansaas.user.permission.service.RoleMenuService;
import com.yuansaas.user.permission.service.RoleUserService;
import com.yuansaas.user.role.entity.QRole;
import com.yuansaas.user.role.entity.Role;
import com.yuansaas.user.role.enums.RoleCodeEnum;
import com.yuansaas.user.role.enums.RoleTypeEnum;
import com.yuansaas.user.role.params.FindRoleParam;
import com.yuansaas.user.role.params.SaveRoleParam;
import com.yuansaas.user.role.params.UpdateRoleParam;
import com.yuansaas.user.role.repository.RoleRepository;
import com.yuansaas.user.role.service.RoleService;
import com.yuansaas.user.role.vo.RoleListVo;
import com.yuansaas.user.role.vo.RoleVo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    private final RoleMenuService roleMenuService;
    private final RoleUserService roleUserService;


    /**
     * 列表查询
     *
     * @param findRoleParam 查询参数
     */
    @Override
    public RPage<RoleListVo> getByPage(FindRoleParam findRoleParam) {

        QRole role = QRole.role;
        QSysDept dept = QSysDept.sysDept;

//        QueryResults<RoleListVo> deptName = jpaQueryFactory.select(Projections.bean(RoleListVo.class,
//                        role.id,
//                        role.name,
//                        role.description,
//                        role.deptId,
//                        role.createAt,
//                        role.createBy,
//                        dept.name.as("deptName")
//                ))
//                .from(role)
//                .leftJoin(dept).on(role.deptId.eq(dept.id))
//                .where(BoolBuilder.getInstance()
//                        .and(findRoleParam.getName() , role.name::contains)
//                        .and(findRoleParam.getShopCode() , role.shopCode::eq)
//                        .getWhere())
//                .orderBy(role.id.desc())
//                .offset(findRoleParam.obtainOffset())
//                .limit(findRoleParam.getPageSize())
//                .fetchResults();
//        return findRoleParam.getRPage(deptName.getResults() , deptName.getTotal());

        BooleanBuilder where = BoolBuilder.getInstance()
                .and(findRoleParam.getName(), role.name::contains)
                .and(findRoleParam.getShopCode(), role.shopCode::eq)
                .getWhere();

        return findRoleParam.getPage(() ->{
                    return jpaQueryFactory.select(Projections.bean(RoleListVo.class,
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
                    .where(where);

                }, () ->{
                    return jpaQueryFactory.select(role.id.count())
                           .from(role)
                           .where(where);
                }
        );
    }

    /**
     * 新增角色
     *
     * @param saveRoleParam 新增参数
     */
    @Override
    public Role save(SaveRoleParam saveRoleParam) {
        Long deptId = validated(saveRoleParam.getShopCode());
        Role role = new Role();
        BeanUtil.copyProperties(saveRoleParam, role);
        role.setDeptId(deptId);
        role.init();
        return roleRepository.save(role);
    }

    /**
     * 修改角色
     *
     * @param updateRoleParam 修改参数
     */
    @Override
    public Boolean update(UpdateRoleParam updateRoleParam) {
        roleRepository.findById(updateRoleParam.getId()).ifPresentOrElse(role ->{
            validated(role.getShopCode());
            role.setName(updateRoleParam.getName());
            role.setDescription(updateRoleParam.getDescription());
            role.update();
            roleRepository.save(role);
        } , () ->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("角色不存在");
        });
        return true;
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    @Override
    @Transactional
    public Boolean delete(Long id) {
        // 删除角色
        Role role = roleRepository.findById(id).orElseThrow(DataErrorCode.DATA_NOT_FOUND::buildException);
        role.setDeleteStatus(AppConstants.Y);
        role.update();
        roleRepository.save(role);
        // 删除角色菜单关联和缓存
        roleMenuService.deleteByRoleIds(id);
        // 删除角色用户关联和用户通过角色授权的菜单缓存
        roleUserService.deleteByRoleIds(id);
        return true;
    }

    /**
     * 角色详情
     *
     * @param id 角色id
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
     * 通过角色id查询角色信息
     *
     * @param ids 角色id列表
     * @return 角色列表
     */
    @Override
    public List<Role> getByIdAll(List<Long> ids) {
        return roleRepository.findAllById(ids);
    }

    /**
     * 判断角色数组中，是否有超级管理员
     *
     * @param ids 角色编号数组
     * @return 是否有超级管理员
     */
    @Override
    public Boolean hasAnySuperAdmin(List<Long> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        QRole qRole = QRole.role;
        return jpaQueryFactory.selectOne()
                .from(qRole)
                .where(
                        qRole.code.eq(RoleCodeEnum.SUPER_ADMIN.getName())
                                .and(qRole.id.in(ids))
                                .and(qRole.deleteStatus.eq(AppConstants.N))
                                .and(qRole.lockStatus.eq(AppConstants.N))
                )
                .fetchFirst() != null ;
    }


    /**
     * 校验 角色 相关信息
     */
    public Long validated ( String shopCode) {

        List<SysDept> byShopCodeAndPid = deptService.findByShopCodeAndPid(shopCode, AppConstants.ZERO_L);
        if (ObjectUtil.isEmpty(byShopCodeAndPid)) {
            throw BizErrorCode.BUSINESS_VALIDATION_FAILED.buildException("默认部门不存在");

        }
        return byShopCodeAndPid.getFirst().getId();
    }
}
