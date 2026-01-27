package com.yuansaas.app.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.common.entity.QSysDictType;
import com.yuansaas.app.common.entity.SysDictType;
import com.yuansaas.app.common.enums.DictPlatformTypeEnum;
import com.yuansaas.app.common.params.FindDictParam;
import com.yuansaas.app.common.params.SaveDictParam;
import com.yuansaas.app.common.params.UpdateDictParam;
import com.yuansaas.app.common.params.UpdateSortParam;
import com.yuansaas.app.common.repository.SysDictTypeRepository;
import com.yuansaas.app.common.service.DictService;
import com.yuansaas.app.common.vo.SysDictTypeVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.page.RPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 字典服务实现类
 *
 * @author LXZ 2025/11/18 09:13
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final SysDictTypeRepository dictRepository;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 创建字典数据
     *
     * @param saveDictParam 创建字典相关参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean createDict(SaveDictParam saveDictParam) {
        SysDictType dict = new SysDictType();
        BeanUtils.copyProperties(saveDictParam, dict);
        dict.setDictCode(getCode(saveDictParam.getPlatform()));
        dict.setPlatform(saveDictParam.getPlatform().name());
        dict.setLockStatus(AppConstants.N);
        dict.setDeleteStatus(AppConstants.N);
        dict.setIsSysDefault(AppConstants.N);
        dict.setCreateBy(AppContextUtil.getUserInfo());
        dict.setCreateAt(LocalDateTime.now());
        dict.setUpdateBy(AppContextUtil.getUserInfo());
        dict.setUpdateAt(LocalDateTime.now());
        dictRepository.save(dict);
        return true;
    }

    /**
     * 编辑字典
     *
     * @param updateDictParam 编辑字典相关参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean updateDict( UpdateDictParam updateDictParam) {
        // 查询字典数据
        SysDictType sysDictType = getDictType(updateDictParam.getId());
        // 更新字典数据
        sysDictType.setDictName(updateDictParam.getDictName());
        sysDictType.setUpdateBy(AppContextUtil.getUserInfo());
        sysDictType.setUpdateAt(LocalDateTime.now());
        dictRepository.save(sysDictType);
        return true;
    }

    /**
     * 修改字典排序
     *
     * @param updateSortParam 字典数据添加参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean updateOrderNum(UpdateSortParam updateSortParam) {
        SysDictType sysDictType = getDictType(updateSortParam.getId());
        sysDictType.setSort(updateSortParam.getSort());
        sysDictType.setUpdateBy(AppContextUtil.getUserInfo());
        sysDictType.setUpdateAt(LocalDateTime.now());
        dictRepository.save(sysDictType);
        return true;
    }

    /**
     * 根据字典id删除字典数据
     *
     * @param id 字典id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean deleteDict(Long id) {
        SysDictType dictType = getDictType(id);
        dictType.setDeleteStatus(AppConstants.Y);
        dictType.setUpdateAt(LocalDateTime.now());
        dictType.setUpdateBy(AppContextUtil.getUserInfo());
        dictRepository.save(dictType);
        return true;
    }

    /**
     * 操作禁用/启用字典
     *
     * @param id 字典id
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public Boolean lock(Long id) {
        SysDictType dictType = getDictType(id);
        dictType.setLockStatus(AppConstants.N.equals(dictType.getLockStatus()) ? AppConstants.Y : AppConstants.N);
        dictType.setUpdateAt(LocalDateTime.now());
        dictType.setUpdateBy(AppContextUtil.getUserInfo());
        dictRepository.save(dictType);
        return true;
    }

    /**
     * 根据查询条件返回字典列表
     * @param findDictParam 查询条件参数
     * @author lxz 2025/11/16 14:35
     */
    @Override
    public RPage<SysDictTypeVo> getDictListByPage(FindDictParam findDictParam) {
        // 查询条件
        QSysDictType qSysDictType = QSysDictType.sysDictType;
        QueryResults<SysDictTypeVo> pageDict = jpaQueryFactory.select(Projections.bean(SysDictTypeVo.class,
                        qSysDictType.id,
                        qSysDictType.dictName,
                        qSysDictType.isSysDefault,
                        qSysDictType.lockStatus,
                        qSysDictType.dictCode,
                        qSysDictType.platform,
                        qSysDictType.sort,
                        qSysDictType.updateBy,
                        qSysDictType.updateAt
                )).from(qSysDictType)
                .where(BoolBuilder.getInstance()
                        .and(findDictParam.getDictName(), qSysDictType.dictName::contains)
                        .and(ObjectUtil.isNotEmpty(findDictParam.getPlatform()) ? findDictParam.getPlatform().name() : null, qSysDictType.platform::eq)
                        .and(AppConstants.N  , qSysDictType.deleteStatus::eq)
                        .and(findDictParam.getLockStatus() , qSysDictType.lockStatus::eq)
                        .getWhere())
                .orderBy(qSysDictType.sort.asc() , qSysDictType.createAt.desc())
                .offset(findDictParam.obtainOffset())
                .limit(findDictParam.getPageSize())
                .fetchResults();
        return new RPage<>(findDictParam.getPageNo(), findDictParam.getPageSize(), pageDict.getResults(), pageDict.getTotal());
    }


    /**
     * 根据id获取字典数据
     * @param id 字典id
     * @author lxz 2025/11/16 14:35
     */
    private SysDictType getDictType(Long id){
        SysDictType sysDictType = dictRepository.findById(id).orElse(null);
        if (ObjectUtil.isEmpty(sysDictType)) {
            throw DataErrorCode.DATA_VALIDATION_FAILED.buildException("字典数据不存在");
        }
        return sysDictType;
    }



    /**
     * 生成 字典code
     */
    private String getCode(DictPlatformTypeEnum platformTypeEnum) {
        // 生成code
        String code = platformTypeEnum.name() + "_" + RandomUtil.randomStringUpper(4);
        // 判断code是否存在
        Integer count = dictRepository.countByDictCode(code);
        if (count > 0) {
            getCode(platformTypeEnum);
        }
        return code;
    }
}
