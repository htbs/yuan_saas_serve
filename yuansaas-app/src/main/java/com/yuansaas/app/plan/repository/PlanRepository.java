package com.yuansaas.app.plan.repository;

import com.yuansaas.app.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * 套餐信息表 - 数据库操作
 *
 * @author LXZ 2026/2/1 16:30
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {
    /**
     *  查询套餐code是否存在
     */
    @Query(value = "select count(1) from plan where plan_code = :planCode and delete_status = 'N' " , nativeQuery = true)
    Integer countByPlanCode(@Param("planCode") String planCode);
}
