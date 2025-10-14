package com.yuansaas.user.system.service.impl;

import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.user.common.enums.UserStatus;
import com.yuansaas.user.system.entity.SysUser;
import com.yuansaas.user.system.repository.SysUserRepository;
import com.yuansaas.user.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
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


    @Override
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById( id);
    }

    @Override
    public Optional<SysUser> findByUsername(String username) {
        return sysUserRepository.findByUserName(username);
    }

    @Override
    public void lockUser(Long userId) {
        sysUserRepository.findById(userId).ifPresentOrElse(sysUser -> {
            sysUser.setStatus(UserStatus.suspended.name());
            sysUser.setUpdateAt(LocalDateTime.now());
            sysUser.setUpdateBy(AppContextUtil.getUserInfo());
            sysUserRepository.save(sysUser);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在");
        });
    }

    @Override
    public void unlockUser(Long userId) {
        sysUserRepository.findById(userId).ifPresentOrElse(sysUser -> {
            sysUser.setStatus(UserStatus.active.name());
            sysUser.setUpdateAt(LocalDateTime.now());
            sysUser.setUpdateBy(AppContextUtil.getUserInfo());
            sysUserRepository.save(sysUser);
        }, () -> {
            throw  DataErrorCode.DATA_NOT_FOUND.buildException("用户不存在");
        });
    }
}
