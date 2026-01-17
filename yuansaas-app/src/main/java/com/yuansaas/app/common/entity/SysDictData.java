package com.yuansaas.app.common.entity;

import com.yuansaas.app.common.enums.DictPlatformTypeEnum;
import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 字典数据
 *
 * @author LXZ 2025/11/17 15:46
 */
@Data
@Entity
@Table(name = "sys_dict_data")
public class SysDictData extends BaseEntity {

    /**
     * 字典code
     */
    private  String  dictCode;
    /**
     * 字典标签
     */
    private  String  dictLabel;
    /**
     * 字典键值
     */
    private  String  dictValue;
    /**
     * 字典排序
     */
    private  Integer sort;
    /**
     * 是否系统默认 （Y 是|N 否）
     */
    private String isSysDefault = "N";
}
