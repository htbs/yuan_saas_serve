package com.yuansaas.app.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.common.entity.QSysDictData;
import com.yuansaas.app.common.entity.SysDictData;
import com.yuansaas.app.common.entity.SysDictType;
import com.yuansaas.app.common.enums.CacheEnum;
import com.yuansaas.app.common.params.*;
import com.yuansaas.app.common.repository.SysDictDataRepository;
import com.yuansaas.app.common.repository.SysDictTypeRepository;
import com.yuansaas.app.common.service.DictItemService;
import com.yuansaas.app.common.vo.SysDictDataVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import com.yuansaas.core.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
        SysDictType byDictCode = sysDictTypeRepository.findByDictCode(saveDictItemParam.getDictCode());
        if (ObjectUtil.isEmpty(byDictCode)) {
            throw DataErrorCode.DATA_VALIDATION_FAILED.buildException("字典数据不存在！");
        }
        // 验证字典标签是否存在
        validateDictLabel(null,saveDictItemParam.getDictCode(), saveDictItemParam.getDictLabel());
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(saveDictItemParam, sysDictData);
        sysDictData.setLockStatus(AppConstants.N);
        sysDictData.setDeleteStatus(AppConstants.N);
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
        validateDictLabel(sysDictData.getId(),sysDictData.getDictCode(), updateDictItemParam.getDictLabel());
        sysDictData.setDictValue(updateDictItemParam.getDictValue());
        sysDictData.setDictLabel(updateDictItemParam.getDictLabel());
        sysDictData.setSort(updateDictItemParam.getSort());
        sysDictData.setUpdateAt(LocalDateTime.now());
        sysDictData.setUpdateBy(AppContextUtil.getUserInfo());
        sysDictDataRepository.save(sysDictData);
        setCache(sysDictData);
        return true;
    }

//    /**
//     * 修改字典排序
//     *
//     * @param updateSortParam 修改字典排序
//     * @author lxz 2025/11/16 14:35
//     */
//    @Override
//    public Boolean updateOrderNum(UpdateSortParam updateSortParam) {
//        SysDictData sysDictData = sysDictDataRepository.findById(updateSortParam.getId()).orElseThrow(() -> DataErrorCode.DATA_NOT_FOUND.buildException("字典数据不存在"));
//        sysDictData.setSort(updateSortParam.getSort());
//        sysDictData.setUpdateAt(LocalDateTime.now());
//        sysDictData.setUpdateBy(AppContextUtil.getUserInfo());
//        sysDictDataRepository.save(sysDictData);
//        setCache(sysDictData);
//        return true;
//    }

    /**
     * 根据字典id删除字典数据
     *
     * @param id 字典id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean deleteDict(Long id) {
        SysDictData sysDictData = sysDictDataRepository.findById(id).orElseThrow(DataErrorCode.DATA_NOT_FOUND::buildException);
        sysDictData.setDeleteStatus(AppConstants.Y);
        sysDictData.setUpdateAt(LocalDateTime.now());
        sysDictData.setUpdateBy(AppContextUtil.getUserInfo());
        sysDictDataRepository.save(sysDictData);
        deleteCache(sysDictData);
        return true;
    }

    /**
     * 操作字典项的禁用和启用状态
     *
     * @param id 字典id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean lock(Long id) {
        SysDictData sysDictData = sysDictDataRepository.findById(id).orElseThrow(DataErrorCode.DATA_NOT_FOUND::buildException);
        sysDictData.setLockStatus(AppConstants.N.equals(sysDictData.getLockStatus()) ? AppConstants.Y : AppConstants.N);
        sysDictData.setUpdateAt(LocalDateTime.now());
        sysDictData.setUpdateBy(AppContextUtil.getUserInfo());
        sysDictDataRepository.save(sysDictData);
        setCache(sysDictData);
        return true;
    }

    @Override
    public RPage<SysDictDataVo> findByPage(FindDictItemParam findDictItemParam) {
        QSysDictData qSysDictData = QSysDictData.sysDictData;

        QueryResults<SysDictDataVo> queryResults = jpaQueryFactory.select(Projections.bean(SysDictDataVo.class,
                        qSysDictData.id,
                        qSysDictData.dictCode,
                        qSysDictData.dictLabel,
                        qSysDictData.dictValue,
                        qSysDictData.sort,
                        qSysDictData.isSysDefault,
                        qSysDictData.lockStatus,
                        qSysDictData.createBy,
                        qSysDictData.createAt,
                        qSysDictData.updateAt,
                        qSysDictData.updateBy,
                        qSysDictData.remark
                )).from(qSysDictData)
                .where(BoolBuilder.getInstance()
                        .and(findDictItemParam.getDictCode()  , qSysDictData.dictCode::eq)
                        .and(findDictItemParam.getDictValue() , qSysDictData.dictValue::contains)
                        .and(AppConstants.N , qSysDictData.deleteStatus::eq)
                        .getWhere())
                .orderBy(qSysDictData.sort.asc(), qSysDictData.updateAt.desc())
                .limit(findDictItemParam.getPageSize())
                .offset(findDictItemParam.obtainOffset())
                .fetchResults();
        return new RPage<>(findDictItemParam.getPageNo(), findDictItemParam.getPageSize(),queryResults.getResults() , queryResults.getTotal());
    }

    /**
     * 查询字典项数据
     *
     * @param dictCode  字典code
     * @param dictLabel 字典keu
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public SysDictDataVo findByDictCodeAndDictLabel(String dictCode, String dictLabel) {
        SysDictDataVo sysDictDataVo = new SysDictDataVo();
        BeanUtils.copyProperties(getCache(dictCode , dictLabel) , sysDictDataVo);
        return  sysDictDataVo;
    }

    /**
     * 验证字典标签是否存在
     * @param dictCode 字典code
     * @param dictLabel 字典标签
     */
    private void validateDictLabel(Long dictItemId,String dictCode , String dictLabel) {
        SysDictData dictData = sysDictDataRepository.findByDictLabelAndDictCode(dictLabel, dictCode);
        if (ObjectUtil.isNotEmpty(dictData)) {
            if (!ObjectUtil.equals(dictData.getId(), dictItemId)) {
                throw DataErrorCode.DATA_VALIDATION_FAILED.buildException("字典标签已存在");
            }
        }
    }

    /**
     * 从缓存中获取
     */
    private SysDictData getCache(String dictCode , String  dictLabel){
        String key =  RedisUtil.genKey(CacheEnum.DICT.getKey(), dictCode, dictLabel);
        return RedisUtil.getOrLoad(key, new TypeReference<SysDictData>() {},
                () -> {
                    SysDictData byDictLabelAndDictCode = sysDictDataRepository.findByDictLabelAndDictCode(dictLabel, dictCode);
                    if (ObjectUtil.isEmpty(byDictLabelAndDictCode)) {
                        throw DataErrorCode.DATA_NOT_FOUND.buildException();
                    }
                    return byDictLabelAndDictCode;
                }
        );
    }

    /**
     * 保存字段项数据到缓存中
     */
    private void setCache(SysDictData sysDictData){
        String key =RedisUtil.genKey(CacheEnum.DICT.getKey(), sysDictData.getDictCode(),sysDictData.getDictLabel());
        // 删除key
        RedisUtil.delete(key);
        // 保存数据
        RedisUtil.set(key , sysDictData);
    }

    /**
     * 保存字段项数据到缓存中
     */
    private void deleteCache(SysDictData sysDictData){
        String key =RedisUtil.genKey(CacheEnum.DICT.getKey(), sysDictData.getDictCode(),sysDictData.getDictLabel());
        // 删除key
        RedisUtil.delete(key);
    }
}
