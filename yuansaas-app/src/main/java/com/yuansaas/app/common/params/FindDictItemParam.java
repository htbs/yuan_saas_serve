package com.yuansaas.app.common.params;

import com.yuansaas.core.page.PageModel;
import lombok.Data;

/**
 *
 * 查询字典类型
 *
 * @author LXZ 2025/11/19 18:38
 */
@Data
public class FindDictItemParam extends PageModel {
    /**
     * 字典项名称
     */
    private String dictValue;
    /**
     * 字典类型ID
     */
    private Long dictTypeId;
}
