package com.yuansaas.user.role.repository;

import com.yuansaas.user.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 *
 * @author LXZ 2025/10/21 10:39
 */
public interface RoleRepository extends JpaRepository<Role, Long> {


    List<Role> findByIdInAndDeleteStatus(List<Long> id , String deleteStatus);
}
