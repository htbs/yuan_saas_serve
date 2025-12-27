package com.yuansaas.app.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统字典类型VO
 *
 * @author LXZ 2025/11/19 17:31
 */
@Data
public class SysDictTypeVo implements Serializable {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典名称
     */
    private String dictValue;
    /**
     *  排序
     */
    private Integer sort;
    /**
     * 操作人
     */
    private String updateBy;
}
