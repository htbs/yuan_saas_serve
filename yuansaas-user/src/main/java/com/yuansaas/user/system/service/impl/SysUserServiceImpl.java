package com.yuansaas.user.system.service.impl;

import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.user.common.enums.UserStatus;
import com.yuansaas.user.system.entity.SysUser;
import com.yuansaas.user.system.param.SysUserCreateParam;
import com.yuansaas.user.system.repository.SysUserRepository;
import com.yuansaas.user.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Lazy
    private final PasswordEncoder passwordEncoder;


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
    public SysUser createUser(SysUserCreateParam sysUserCreateParam) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserCreateParam, sysUser);
        sysUser.setPassword(passwordEncoder.encode(sysUserCreateParam.getPassword()));
        sysUser.setCreateAt(LocalDateTime.now());
        sysUser.setCreateBy(AppContextUtil.getUserInfo());
        sysUserRepository.save(sysUser);
        return sysUser;
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
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在");
        });
        return true;
    }
}
