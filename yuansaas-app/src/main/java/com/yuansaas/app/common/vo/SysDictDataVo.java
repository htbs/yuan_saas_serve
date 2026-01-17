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
public class SysDictDataVo implements Serializable {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典类型
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
     * 是否系统默认 （Y 是 |N 否）
     */
    private String isSysDefault;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createAt;
    /**
     * 修改时间
     */
    private LocalDateTime updateAt;
    /**
     * 修改人
     */
    private String updateBy;

}
