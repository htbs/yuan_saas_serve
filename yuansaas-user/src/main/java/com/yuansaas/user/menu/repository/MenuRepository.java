package com.yuansaas.user.menu.repository;

import com.yuansaas.user.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 菜单连接数据库操作管理
 *
 * @author LXZ 2025/10/21 11:47
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * 查询菜单code是否存在
     * @param menuCode 菜单code
     */
    Long countByAndMenuCode(String menuCode);

}
