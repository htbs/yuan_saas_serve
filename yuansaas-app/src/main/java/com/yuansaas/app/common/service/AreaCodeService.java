package com.yuansaas.app.common.service;

import com.yuansaas.app.common.vo.AreaDataVo;

import java.util.List;

/**
 *
 * 地区管理
 *
 * @author LXZ 2026/1/20 17:01
 */
public interface AreaCodeService {

    void into(Integer level , Integer pageNo , Integer pageSize);

    /**
     * 获取省列表
     * @param name 地区名字
     * @author LXZ 2026/1/20  18:02
     */
    List<AreaDataVo> getProvinces(String name);

    /**
     * 根据code和名字查询子级地区code
     * @param code 父级地区code
     * @param name 子级地区名字
     * @author LXZ 2026/1/20  18:02
     */
    List<AreaDataVo> getSublevelByCodeAndName(Long code, String name);

}
