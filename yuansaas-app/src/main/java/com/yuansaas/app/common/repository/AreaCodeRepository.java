package com.yuansaas.app.common.repository;

import com.yuansaas.app.common.entity.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * 地区管理数据库操作
 *
 * @author LXZ 2026/1/20 17:21
 */
public interface AreaCodeRepository extends JpaRepository<AreaCode , Long> {


}
