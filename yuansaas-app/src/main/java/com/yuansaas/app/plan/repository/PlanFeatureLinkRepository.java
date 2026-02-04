package com.yuansaas.app.plan.repository;

import com.yuansaas.app.plan.entity.PlanFeatureLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * 套餐功能关系表 - 数据库操作
 *
 * @author LXZ 2026/2/1 16:30
 */
public interface PlanFeatureLinkRepository extends JpaRepository<PlanFeatureLink, Long> {


    @Query(value = "select feature_code from plan_feature_link where plan_code = ?1" , nativeQuery = true)
    List<Long> findFeatureCodeListByPlanCode(String planCode);

}
