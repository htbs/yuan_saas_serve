package com.yuansaas.user.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.user.role.entity.Role;
import com.yuansaas.user.role.entity.RoleUser;
import com.yuansaas.user.role.repository.RoleRepository;
import com.yuansaas.user.role.repository.RoleUserRepository;
import com.yuansaas.user.role.service.RoleService;
import com.yuansaas.user.role.service.RoleUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 角色用户关系的service实现类
 * @author LXZ 2025/10/25 17:16
 */
@Service
@RequiredArgsConstructor
public class RoleUserServiceImpl implements RoleUserService {

    private final RoleUserRepository roleUserRepository;
    private final RoleRepository roleRepository;

    /**
     * 保存或修改
     *
     * @param userId     用户ID
     * @param roleIdList 角色ID列表
     */
    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        // 先删除原有关系
        roleUserRepository.deleteByUserId(userId);
        //用户没有一个角色权限的情况
        List<Role> byIdAll = roleRepository.findAllById(roleIdList);
        if(ObjectUtil.isEmpty(byIdAll)){
            return ;
        }
        //保存角色用户关系
        List<RoleUser> roleUserList = CollUtil.newArrayList();
        for(Role roleId : byIdAll){
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(userId);
            roleUser.setRoleId(roleId.getId());
            //保存
            roleUserList.add(roleUser);
        }
        roleUserRepository.saveAll(roleUserList);
    }

    /**
     * 根据角色ids，删除角色用户关系
     *
     * @param roleIds 角色ids
     */
    @Override
    public void deleteByRoleIds(Long roleIds) {
        roleUserRepository.deleteByRoleId(roleIds);
    }

    /**
     * 根据用户id，删除角色用户关系
     *
     * @param userIds 用户ids
     */
    @Override
    public void deleteByUserIds(Long userIds) {
        roleUserRepository.deleteByUserId(userIds);
    }

    /**
     * 角色ID列表
     *
     * @param userId 用户ID
     */
    @Override
    public List<Long> getRoleIdList(Long userId) {
        return roleUserRepository.findByUserId(userId).stream().map(RoleUser::getRoleId).toList();
    }
}
