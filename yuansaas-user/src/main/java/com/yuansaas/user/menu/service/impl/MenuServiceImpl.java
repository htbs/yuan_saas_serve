package com.yuansaas.user.menu.service.impl;

import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.user.menu.entity.Menu;
import com.yuansaas.user.menu.params.FindMenuParam;
import com.yuansaas.user.menu.params.SaveMenuParam;
import com.yuansaas.user.menu.params.UpdateMenuParam;
import com.yuansaas.user.menu.repository.MenuRepository;
import com.yuansaas.user.menu.service.MenuService;
import com.yuansaas.user.menu.vo.MenuListVo;
import com.yuansaas.user.menu.vo.MenuVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * 菜单服务
 *
 * @author LXZ 2025/10/21 12:02
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;


    /**
     * 列表查询
     *
     * @param findMenuParam 查询参数
     *
     */
    @Override
    public List<MenuListVo> list(FindMenuParam findMenuParam) {
        return List.of();
    }

    /**
     * 新增菜单
     *
     * @param saveMenuParam 新增参数
     */
    @Override
    public Boolean save(SaveMenuParam saveMenuParam) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(saveMenuParam, menu);
        menu.setCreateAt(LocalDateTime.now());
        menu.setCreateBy("admin");
        menuRepository.save(menu);
        return true;
    }

    /**
     * 修改菜单
     *
     * @param updateMenuParam 修改参数
     */
    @Override
    public Boolean update(UpdateMenuParam updateMenuParam) {
        menuRepository.findById(updateMenuParam.getId()).ifPresentOrElse(menu->{
            menu.setName(updateMenuParam.getMenuName());
            menu.setUrl(updateMenuParam.getMenuUrl());
            menu.setSort(updateMenuParam.getSort());
            menu.setIcon(updateMenuParam.getMenuIcon());
            menu.setUpdateAt(LocalDateTime.now());
            menu.setUpdateBy("admin");
            menuRepository.save(menu);
        } ,()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("菜单不存在");
        });
        return true;
    }

    /**
     * 删除菜单
     *
     * @param id    菜单id
     */
    @Override
    public Boolean delete(Long id) {
        menuRepository.findById(id).ifPresentOrElse(menu->{
            menu.setDeleteStatus(AppConstants.N.equals(menu.getDeleteStatus()) ?  AppConstants.Y : AppConstants.N);
            menu.setUpdateAt(LocalDateTime.now());
            menu.setUpdateBy("admin");
            menuRepository.save(menu);
        } ,()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("菜单不存在");
        });
        return true;
    }

    /**
     * 禁用菜单
     *
     * @param id 菜单id
     */
    @Override
    public Boolean lock(Long id) {
        menuRepository.findById(id).ifPresentOrElse(menu->{
            menu.setLockStatus(AppConstants.N.equals(menu.getLockStatus()) ?  AppConstants.Y : AppConstants.N);
            menu.setUpdateAt(LocalDateTime.now());
            menu.setUpdateBy("admin");
            menuRepository.save(menu);
        } ,()->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("菜单不存在");
        });
        return true;
    }

    /**
     * 菜单详情
     *
     * @param id 菜单id
     */
    @Override
    public MenuVo getById(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("菜单不存在"));
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(menu, menuVo);
        return menuVo;
    }
}
