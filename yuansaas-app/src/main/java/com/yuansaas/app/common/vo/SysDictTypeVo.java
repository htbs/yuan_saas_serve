package com.yuansaas.app.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统字典VO
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
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典禁用状态
     */
    private String lockStatus;
    /**
     *  排序
     */
    private Integer sort;
    /**
     * 操作人
     */
    private String updateBy;
    /**
     * 操作人
     */
    private LocalDateTime updateAt;
}
