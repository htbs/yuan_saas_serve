package com.yuansaas.user.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
import com.yuansaas.core.utils.TreeUtils;
import com.yuansaas.user.dept.entity.QSysDept;
import com.yuansaas.user.dept.entity.SysDept;
import com.yuansaas.user.dept.model.DeptTreeModel;
import com.yuansaas.user.dept.params.FindDeptParam;
import com.yuansaas.user.dept.params.SaveDeptParam;
import com.yuansaas.user.dept.params.UpdateDeptParam;
import com.yuansaas.user.dept.repository.DeptRepository;
import com.yuansaas.user.dept.service.DeptService;
import com.yuansaas.user.dept.vo.DeptListVo;
import com.yuansaas.user.dept.vo.DeptTreeListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * 部门管理实现类
 *
 * @author LXZ 2025/10/17 11:59
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptRepository deptRepository;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 列表查询
     *
     * @param findDeptParam 查询参数
     */
    @Override
    public List<DeptTreeListVo> list(FindDeptParam findDeptParam) {
        QSysDept qDept = QSysDept.sysDept;
        List<DeptTreeListVo> deptLists = jpaQueryFactory.select(Projections.bean(DeptTreeListVo.class,
                        qDept.id,
                        qDept.name,
                        qDept.pid,
                        qDept.lockStatus,
                        qDept.createAt,
                        qDept.createBy,
                        qDept.updateAt,
                        qDept.updateBy,
                        qDept.merchantCode))
                .from(qDept)
                .where(BoolBuilder.getInstance()
                        .and(findDeptParam.getMerchantCode() , qDept.merchantCode::eq)
                        .and(findDeptParam.getDeptName() , qDept.name::eq)
                        .and(qDept.lockStatus.eq(AppConstants.N))
                        .and(qDept.deleteStatus.eq(AppConstants.N))
                        .getWhere())
                .fetch();
        if (ObjectUtil.isEmpty(deptLists)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException("部门不存在");
        }
        return TreeUtils.build(deptLists);
    }

    /**
     * 新增部门
     *
     * @param saveDeptParam 新增参数
     */
    @Override
    public Boolean save(SaveDeptParam saveDeptParam) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(saveDeptParam, sysDept);
        sysDept.setCreateBy("admin");
        sysDept.setCreateAt(LocalDateTime.now());
        deptRepository.save(sysDept);
        return true;
    }

    /**
     * 修改部门
     *
     * @param updateDeptParam 修改参数
     */
    @Override
    public Boolean update(UpdateDeptParam updateDeptParam) {
        deptRepository.findById(updateDeptParam.getId()).ifPresentOrElse(dept ->{
            dept.setName(updateDeptParam.getName());
            dept.setUpdateBy("admin");
            dept.setUpdateAt(LocalDateTime.now());
            deptRepository.save(dept);
        } , () ->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("部门不存在");
        });
        return true;
    }

    /**
     * 删除部门
     *
     * @param id 部门id
     */
    @Override
    public Boolean delete(Long id) {
        deptRepository.findById(id).ifPresentOrElse(dept ->{
            List<SysDept> byPid = getDeptList(dept.getId());
            byPid.add(dept);
            deptRepository.deleteAll(byPid);
        } , () ->{
            throw DataErrorCode.DATA_NOT_FOUND.buildException("部门不存在");
        });
        return true;
    }

    /**
     * 部门详情
     *
     * @param merchantCode
     * @param id
     */
    @Override
    public DeptListVo getById(String merchantCode, Long id) {
        SysDept sysDept = deptRepository.findByMerchantCodeAndId(merchantCode,id);
        if (ObjectUtil.isEmpty(sysDept)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException("部门不存在");
        }
        return BeanUtil.copyProperties(sysDept , DeptListVo.class);
    }


    /**
     * 获取部门列表 平铺展示
     * @param id 部门id
     * @return 部门列表
     */
    public List<SysDept> getDeptList(Long id) {
        List<SysDept> byPid = deptRepository.findByPid(id);
        List<SysDept> deptList = new ArrayList<>();
        for (SysDept dept : byPid) {
            List<SysDept> children = getDeptList(dept.getId());
            if (!Objects.isNull(children)) {
                deptList.addAll(children);
            }
        }
        deptList.addAll(byPid);
        return deptList;
    }

    /**
     * 获取部门树形结构
     * @param id 部门id
     * @return 部门树形结构
     */
    public List<DeptTreeModel> getDeptTreeList(Long id) {
        List<SysDept> byPid = deptRepository.findByPid(id);
        List<DeptTreeModel> deptTreeList = new ArrayList<>();
        for (SysDept dept : byPid) {
            DeptTreeModel deptTreeModel = new DeptTreeModel();
            BeanUtils.copyProperties(dept, deptTreeModel);
            List<DeptTreeModel> children = getDeptTreeList(dept.getId());
            if (!Objects.isNull(children)) {
                deptTreeModel.setChildrenDeptList(children);
            }
            deptTreeList.add(deptTreeModel);

        }
        return deptTreeList;
    }

}
