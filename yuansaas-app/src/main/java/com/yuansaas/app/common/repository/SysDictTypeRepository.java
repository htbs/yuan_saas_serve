package com.yuansaas.app.common.repository;

import com.yuansaas.app.common.entity.SysDictType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 字典数据访问接口
 *
 * @author LXZ 2025/11/17 15:55
 */
public interface SysDictTypeRepository extends JpaRepository<SysDictType, Long> {

    /**
     * 根据字典类型查询字典类型数量
     */
    Integer countByDictType(String dictType);

    /**
     * 根据字典类型查询字典类型
     */
    SysDictType findByDictType(String dictType);

    /**
     * 根据字典编码查询字典数量
     */
    Integer countByDictCode(String dictCode);
}
