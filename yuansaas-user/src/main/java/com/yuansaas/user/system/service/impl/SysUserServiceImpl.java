package com.yuansaas.user.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.utils.TreeUtils;
import com.yuansaas.user.common.enums.UserStatus;
import com.yuansaas.user.dept.service.DeptUserService;
import com.yuansaas.user.menu.entity.Menu;
import com.yuansaas.user.menu.service.MenuService;
import com.yuansaas.user.menu.vo.MenuListVo;
import com.yuansaas.user.role.service.RoleMenuService;
import com.yuansaas.user.role.service.RoleUserService;
import com.yuansaas.user.system.entity.SysUser;
import com.yuansaas.user.system.param.SysUserCreateParam;
import com.yuansaas.user.system.param.UserUpdateParam;
import com.yuansaas.user.system.repository.SysUserRepository;
import com.yuansaas.user.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 系统用户接口
 *
 * @author HTB 2025/8/8 14:41
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository sysUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleUserService roleUserService;
    private final RoleMenuService roleMenuService;
    private final DeptUserService deptUserService;
    private final MenuService menuService;


    @Override
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById( id);
    }

    @Override
    public Optional<SysUser> findByUsername(String username) {
        return sysUserRepository.findByUserName(username);
    }

    /**
     * 创建用户
     *
     * @param sysUserCreateParam 用户创建请求
     * @return 创建成功的用户信息
     */
    @Override
    public SysUser saveUser(SysUserCreateParam sysUserCreateParam) {
        // 保存用户信息
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserCreateParam, sysUser);
        sysUser.setPassword(passwordEncoder.encode(sysUserCreateParam.getPassword()));
        sysUser.setCreateAt(LocalDateTime.now());
        sysUser.setCreateBy(AppContextUtil.getUserInfo());
        sysUserRepository.save(sysUser);
        // 授权角色权限
        roleUserService.saveOrUpdate(sysUser.getId(), sysUserCreateParam.getRoleList());
        // 授权部门权限
        deptUserService.saveOrUpdate(sysUser.getId(),sysUserCreateParam.getDeptId());
        return sysUser;
    }

    @Override
    public Boolean updateUser(UserUpdateParam userUpdateParam) {
        sysUserRepository.findById(userUpdateParam.getId()).ifPresentOrElse(sysUser -> {
            BeanUtils.copyProperties(userUpdateParam, sysUser);
            sysUser.setUpdateBy(AppContextUtil.getUserInfo());
            sysUser.setUpdateAt(LocalDateTime.now());
            sysUserRepository.save(sysUser);
            // 授权角色权限
            roleUserService.saveOrUpdate(sysUser.getId(), userUpdateParam.getRoleList());
        },()->{
            throw  DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在");
        });
        return true;
    }

    @Override
    public Boolean lockUser(Long userId) {
        sysUserRepository.findById(userId).ifPresentOrElse(sysUser -> {
            sysUser.setStatus(UserStatus.suspended.name());
            sysUser.setUpdateAt(LocalDateTime.now());
            sysUser.setUpdateBy(AppContextUtil.getUserInfo());
            sysUserRepository.save(sysUser);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在");
        });
        return true;
    }

    @Override
    public Boolean unlockUser(Long userId) {
        sysUserRepository.findById(userId).ifPresentOrElse(sysUser -> {
            sysUser.setStatus(UserStatus.active.name());
            sysUser.setUpdateAt(LocalDateTime.now());
            sysUser.setUpdateBy(AppContextUtil.getUserInfo());
            sysUserRepository.save(sysUser);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在");
        });
        return true;
    }

    @Override
    public Boolean deleteUser(Long userId) {
        sysUserRepository.findById(userId).ifPresentOrElse(sysUser -> {
            sysUser.setStatus(UserStatus.deleted.name());
            sysUser.setUpdateAt(LocalDateTime.now());
            sysUser.setUpdateBy(AppContextUtil.getUserInfo());
            sysUserRepository.save(sysUser);
            // 解除角色权限
            roleUserService.deleteByUserIds(userId);
            // 解除部门权限
            deptUserService.deleteByUserId(userId);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在");
        });
        return true;
    }

    /**
     * 根据用户id查询菜单列表
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    @Override
    public List<MenuListVo> findMenuListByUserId(Long userId) {
        // 查询角色列表
        List<Long> roleIdList = roleUserService.getRoleIdList(userId);
        // 查询菜单列表
        List<Long> menuIdList = roleMenuService.getMenuIdList(roleIdList);
        // 构建树形菜单
        List<Menu> menuList = menuService.getByList(menuIdList);
        return TreeUtils.build(BeanUtil.copyToList(menuList, MenuListVo.class) , AppConstants.ZERO_L);
    }
}
