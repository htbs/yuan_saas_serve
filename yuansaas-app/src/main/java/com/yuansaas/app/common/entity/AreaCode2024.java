package com.yuansaas.app.common.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 地区
 * @author LXZ 2026/1/20 16:49
 */
@Data
@Entity
@Table(name = "area_code_2024")
public class AreaCode2024{
    /**
     * 区划代码
     */
    @Id
    private Long code;
    /**
     * 名称
     */
    private String name;
    /**
     * 级别1-5,省市县镇村
     */
    private Integer level;
    /**
     * 父级区划代码
     */
    private Long pcode;
    /**
     * 城乡分类
     */
    private Integer category;
}
