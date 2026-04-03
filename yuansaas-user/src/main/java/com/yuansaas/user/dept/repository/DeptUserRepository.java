package com.yuansaas.user.dept.repository;

import com.yuansaas.user.dept.entity.SysDeptUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  部门用户关系表 - 数据库操作
 *
 * @author LXZ 2025/10/27 16:15
 */
public interface DeptUserRepository extends JpaRepository<SysDeptUser, Long> {

    /**
     * 根据部门id删除记录
     */
    void deleteByDeptId(Long deptId);
    /**
     * 根据用户id删除记录
     */
    void deleteByUserId(Long userId);
    /**
     * 获取部门的关联的用户
     */
    List<SysDeptUser> findByDeptId(Long deptId);
    /**
     * 获取用户关联的部门信息
     */
    SysDeptUser findByUserId(Long userId);
}
