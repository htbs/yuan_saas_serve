package com.yuansaas.app.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.common.entity.QSysDictData;
import com.yuansaas.app.common.entity.SysDictData;
import com.yuansaas.app.common.params.*;
import com.yuansaas.app.common.repository.SysDictDataRepository;
import com.yuansaas.app.common.repository.SysDictTypeRepository;
import com.yuansaas.app.common.service.DictItemService;
import com.yuansaas.app.common.vo.SysDictTypeVo;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 *
 * 字典数据配置
 *
 * @author LXZ 2025/11/20 10:57
 */
@Service
@RequiredArgsConstructor
public class DictItemServiceImpl implements DictItemService {

    private final JPAQueryFactory jpaQueryFactory;
    private final SysDictDataRepository sysDictDataRepository;
    private final SysDictTypeRepository sysDictTypeRepository;


    /**
     * 创建字典数据
     *
     * @param saveDictItemParam 创建字典相关参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean createDict(SaveDictItemParam saveDictItemParam) {
        sysDictTypeRepository.findById(saveDictItemParam.getDictTypeId()).orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("字典类型不存在"));
        // 验证字典标签是否存在
        validateDictLabel(null,saveDictItemParam.getDictTypeId(), saveDictItemParam.getDictLabel());
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(saveDictItemParam, sysDictData);
        sysDictData.setCreateAt(LocalDateTime.now());
        sysDictData.setCreateBy(AppContextUtil.getUserInfo());
        sysDictDataRepository.save(sysDictData);
        return true;
    }

    /**
     * 编辑字典
     *
     * @param updateDictItemParam 编辑字典相关参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean updateDict(UpdateDictItemParam updateDictItemParam) {
        SysDictData sysDictData = sysDictDataRepository.findById(updateDictItemParam.getId()).orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("字典数据不存在"));
        // 验证字典标签是否存在
        validateDictLabel(sysDictData.getId(),sysDictData.getDictTypeId(), updateDictItemParam.getDictLabel());
        sysDictData.setDictValue(updateDictItemParam.getDictValue());
        sysDictData.setDictLabel(updateDictItemParam.getDictLabel());
        sysDictData.setUpdateAt(LocalDateTime.now());
        sysDictData.setUpdateBy(AppContextUtil.getUserInfo());
        sysDictDataRepository.save(sysDictData);
        return true;
    }

    /**
     * 修改字典排序
     *
     * @param updateSortParam 修改字典排序
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean updateOrderNum(UpdateSortParam updateSortParam) {
        SysDictData sysDictData = sysDictDataRepository.findById(updateSortParam.getId()).orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("字典数据不存在"));
        sysDictData.setSort(updateSortParam.getSort());
        sysDictData.setUpdateAt(LocalDateTime.now());
        sysDictData.setUpdateBy(AppContextUtil.getUserInfo());
        sysDictDataRepository.save(sysDictData);
        return true;
    }

    /**
     * 根据字典id删除字典数据
     *
     * @param id 字典id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    @Transactional
    public Boolean deleteDict(Long id) {
        sysDictDataRepository.deleteById(id);
        return true;
    }

    @Override
    public RPage<SysDictTypeVo> findByPage(FindDictItemParam findDictItemParam) {
        QSysDictData qSysDictData = QSysDictData.sysDictData;

        QueryResults<SysDictTypeVo> queryResults = jpaQueryFactory.select(Projections.bean(SysDictTypeVo.class,
                        qSysDictData.id,
                        qSysDictData.dictLabel,
                        qSysDictData.dictValue,
                        qSysDictData.sort,
                        qSysDictData.updateAt,
                        qSysDictData.updateAt
                )).from(qSysDictData)
                .where(BoolBuilder.getInstance()
                        .and(findDictItemParam.getDictTypeId()  , qSysDictData.dictTypeId::eq)
                        .and(findDictItemParam.getDictValue() , qSysDictData.dictValue::contains)
                        .getWhere())
                .orderBy(qSysDictData.sort.desc(), qSysDictData.updateAt.desc())
                .limit(findDictItemParam.getPageSize())
                .offset(findDictItemParam.obtainOffset())
                .fetchResults();
        return new RPage<>(findDictItemParam.getPageNo(), findDictItemParam.getPageSize(),queryResults.getResults() , queryResults.getTotal());
    }

    /**
     * 验证字典标签是否存在
     * @param dictTypeId 字典类型id
     * @param dictLabel 字典标签
     */
    private void validateDictLabel(Long dictItemId,Long dictTypeId , String dictLabel) {
        SysDictData dictData = sysDictDataRepository.findByDictLabelAndDictTypeId(dictLabel, dictTypeId);
        if (ObjectUtil.isNotEmpty(dictData)) {
            if (!ObjectUtil.equals(dictData.getId(), dictItemId)) {
                throw DataErrorCode.DATA_VALIDATION_FAILED.buildException("字典标签已存在");
            }
        }
    }
}
