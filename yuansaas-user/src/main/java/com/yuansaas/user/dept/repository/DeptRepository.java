package com.yuansaas.user.dept.repository;

import com.yuansaas.user.dept.entity.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 *
 * @author LXZ 2025/10/17 12:07
 */
public interface DeptRepository extends JpaRepository<SysDept, Long> {

    /**
     * 根据父部门ID查询子部门
     * @param pid 父部门ID
     * @return 子部门列表
     */
    List<SysDept> findByPid(Long pid);


}
