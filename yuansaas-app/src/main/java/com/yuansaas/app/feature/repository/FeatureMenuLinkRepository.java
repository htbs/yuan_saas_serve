package com.yuansaas.app.feature.repository;

import com.yuansaas.app.feature.entity.FeatureMenuLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * 功能菜单关联表 - 数据库操作
 *
 * @author LXZ 2026/1/29 17:47
 */
public interface FeatureMenuLinkRepository extends JpaRepository<FeatureMenuLink, Long> {

    @Query(value = "Select id from feature_menu_link where feature_code = ?1 and lock_status = ?2" , nativeQuery = true)
    List<Long> featIdByFeatureCodeAndLockStatus(String featureCode  , String lockStatus);
}
