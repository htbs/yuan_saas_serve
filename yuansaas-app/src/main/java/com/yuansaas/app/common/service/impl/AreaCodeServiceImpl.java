package com.yuansaas.app.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.app.common.entity.*;
import com.yuansaas.app.common.enums.CacheEnum;
import com.yuansaas.app.common.repository.AreaCodeRepository;
import com.yuansaas.app.common.service.AreaCodeService;
import com.yuansaas.app.common.vo.AreaDataVo;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.context.AppContextUtil;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 地区管理实现类
 *
 * @author LXZ 2026/1/20 17:02
 */
@Service
@RequiredArgsConstructor
public class AreaCodeServiceImpl implements AreaCodeService {

    private final JPAQueryFactory jpaQueryFactory;
    private final AreaCodeRepository areaCodeRepository;


    @Override
    public void into(Integer level, Integer pageNo, Integer pageSize) {

        QAreaCode qAreaCode = QAreaCode.areaCode;
        List<Long> longList = jpaQueryFactory.select(qAreaCode.code)
                .from(qAreaCode)
                .where(qAreaCode.level.eq(level))
                .fetch();


        QAreaCode2024 qAreaCode2024 = QAreaCode2024.areaCode2024;
        List<AreaCode2024> areaCode2024QueryResults = jpaQueryFactory.selectFrom(qAreaCode2024)
                .where(BoolBuilder.getInstance()
                        .and(level, qAreaCode2024.level::eq)
                        .and(longList , qAreaCode2024.code::notIn)
                        .getWhere()).fetch();


        if (ObjectUtil.isNotEmpty(areaCode2024QueryResults)) {
            List<AreaCode> areaCodeList = new ArrayList<>();
            areaCode2024QueryResults.forEach(f ->{
                AreaCode areaCode = new AreaCode();
                areaCode.setName(f.getName());
                areaCode.setCode(f.getCode());
                areaCode.setPcode(f.getPcode());
                areaCode.setLevel(f.getLevel());
                areaCode.setCategory(f.getCategory());
                areaCode.setCreateAt(LocalDateTime.now());
                areaCode.setCreateBy(AppContextUtil.getUserInfo());
                areaCodeList.add(areaCode);
            });
            areaCodeRepository.saveAll(areaCodeList);
        }


    }

    /**
     * 获取省列表
     *
     * @param name 地区名字
     * @author LXZ 2026/1/20  18:02
     */
    @Override
    public List<AreaDataVo> getProvinces(String name) {
        List<AreaCode> byPcodeAndName = findByPcodeAndName(AppConstants.ZERO_L, name);
        return BeanUtil.copyToList(byPcodeAndName , AreaDataVo.class);
    }

    /**
     * 根据code和名字查询子级地区code
     *
     * @param code 父级地区code
     * @param name 子级地区名字
     * @author LXZ 2026/1/20  18:02
     */
    @Override
    public List<AreaDataVo> getSublevelByCodeAndName(Long code, String name) {
        List<AreaCode> byPcodeAndName = findByPcodeAndName(code, name);
        return BeanUtil.copyToList(byPcodeAndName , AreaDataVo.class);
    }


    /**
     * 根据父级code和名字模糊查询地区code数据
     * @param pcode 父级code
     * @param name 地区名字
     * @author LXZ 2026/1/20  19:11
     */
    private List<AreaCode> findByPcodeAndName(Long pcode , String name) {
        List<AreaCode> codeList = RedisUtil.getOrLoad(RedisUtil.genKey(CacheEnum.AREA.getKey(), pcode),
                new TypeReference<List<AreaCode>>() {
                },
                () -> {
                    QAreaCode areaCode = QAreaCode.areaCode;
                    List<AreaCode> areaCodeList = jpaQueryFactory.select(areaCode)
                            .from(areaCode)
                            .where(BoolBuilder.getInstance()
                                    .and(pcode, areaCode.pcode::eq)
                                    .getWhere())
                            .fetch();
                    return areaCodeList;
                }
        );
        if (ObjectUtil.isNotEmpty(name)) {
            return codeList.stream().filter(f-> f.getName().contains(name)).toList();
        }
       return codeList;
    }
}
