package com.yuansaas.app.common.entity;

import com.yuansaas.app.common.enums.DictPlatformTypeEnum;
import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 字典类型
 *
 * @author LXZ 2025/11/17 15:51
 */
@Data
@Entity
@Table(name = "sys_dict_type")
public class SysDictType extends BaseEntity {

    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 平台类型
     */
    private String platform;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 系统默认
     */
    private String sysDefault;
    /**
     * '锁定状态(Y锁定|N不锁定)'
     */
    private String  lockStatus;
    /**
     * '删除状态(Y删除|N未删除)'
     */
    private String  deleteStatus;
}
