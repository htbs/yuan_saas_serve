package com.yuansaas.app.common.params;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 字典数据添加参数
 *
 * @author lxz 2025-11-17 09:41
 */
@Data
public class SaveDictItemParam {

    /**
     * 字典code
     */
    @NotNull(message = "字典code不能为空")
    private String dictCode;
    /**
     * 字典key
     */
    @NotNull(message = "字典key不能为空")
    private String dictLabel;
    /**
     * 字典value
     */
    @NotNull(message = "字典value不能为空")
    private String dictValue;
    /**
     * 字典排序值
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
}
