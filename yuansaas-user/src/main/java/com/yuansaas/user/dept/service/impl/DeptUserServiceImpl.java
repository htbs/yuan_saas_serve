package com.yuansaas.user.dept.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.ParamErrorCode;
import com.yuansaas.user.dept.entity.SysDept;
import com.yuansaas.user.dept.entity.SysDeptUser;
import com.yuansaas.user.dept.repository.DeptRepository;
import com.yuansaas.user.dept.repository.DeptUserRepository;
import com.yuansaas.user.dept.service.DeptUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author LXZ 2025/10/27 16:19
 */
@Service
@RequiredArgsConstructor
public class DeptUserServiceImpl implements DeptUserService {

    private final DeptUserRepository deptUserRepository;
    private final DeptRepository     deptRepository;

    /**
     * 保存或修改
     *
     * @param shopCode 商铺code
     * @param userId   用户ID
     * @param deptId 部门id
     */
    @Override
    @Transactional
    public void saveOrUpdate(String shopCode, Long userId, Long deptId) {

        if (ObjectUtil.isEmpty(deptId)) {
            List<SysDept> byshopCodeAndPid = deptRepository.findByShopCodeAndPid(shopCode, AppConstants.ZERO_L);
            if (ObjectUtil.isEmpty(byshopCodeAndPid)) {
                return;
            }
            deptId = byshopCodeAndPid.getFirst().getId();
        } else {
            SysDept byshopCodeAndPid = deptRepository.findByShopCodeAndId(shopCode,deptId);
            if (ObjectUtil.isEmpty(byshopCodeAndPid)) {
                throw ParamErrorCode.PARAMETER_INVALID.buildException("部门id不存在");
            }
        }

        // 删除用户之前的部门关系
        deptUserRepository.deleteByUserId(userId);
        //保存部门用户关系
        SysDeptUser sysDeptUser = new SysDeptUser();
        sysDeptUser.setDeptId(deptId);
        sysDeptUser.setUserId(userId);
        deptUserRepository.save(sysDeptUser);
    }

    /**
     * 根据部门ids，删除部门用户关系
     *
     * @param deptId 角色ids
     */
    @Override
    @Transactional
    public void deleteByDeptIds(Long deptId) {
        deptUserRepository.deleteByDeptId(deptId);
    }

    /**
     * 根据用户id，删除部门用户关系
     *
     * @param userId 用户id
     */
    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        deptUserRepository.deleteByUserId(userId);
    }

    /**
     * 部门ID用户列表
     *
     * @param deptId deptIdID
     */
    @Override
    public List<Long> getUserIdList(Long deptId) {
        return deptUserRepository.findByDeptId(deptId).stream().map(SysDeptUser::getUserId).toList();
    }

    /**
     * 用户ID查询部门ID
     *
     * @param userId deptIdID
     */
    @Override
    public Long getDeptIdList(Long userId) {
        SysDeptUser byUserId = deptUserRepository.findByUserId(userId);
        if (ObjectUtil.isEmpty(byUserId)){
            return null;
        }
        return byUserId.getDeptId();
    }
}
