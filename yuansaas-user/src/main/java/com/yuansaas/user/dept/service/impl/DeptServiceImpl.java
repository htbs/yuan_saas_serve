package com.yuansaas.user.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuansaas.common.constants.AppConstants;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.jpa.querydsl.BoolBuilder;
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
    public DeptTreeListVo list(FindDeptParam findDeptParam) {

        QSysDept qDept = QSysDept.sysDept;
        BoolBuilder boolBuilder = BoolBuilder.getInstance();
        boolBuilder.and(findDeptParam.getMerchantCode() , qDept.merchantCode::eq);
        if (!ObjectUtil.isEmpty(findDeptParam.getMerchantCode()) && Objects.equals(AppConstants.ZERO_S , findDeptParam.getMerchantCode()) && ObjectUtil.isEmpty(findDeptParam.getDeptName())) {
            boolBuilder.and(0L , qDept.pid::eq);
        }else {
            boolBuilder.and(findDeptParam.getDeptName() , qDept.name::eq);
        }

        SysDept deptList = jpaQueryFactory.selectFrom(qDept)
                .where(boolBuilder.and(AppConstants.N, qDept.lockStatus::eq).getWhere())
                .fetchOne();
        if (ObjectUtil.isEmpty(deptList)) {
            throw DataErrorCode.DATA_NOT_FOUND.buildException("部门不存在");
        }
        List<DeptTreeModel> deptTreeList = getDeptTreeList(deptList.getId());
        DeptTreeListVo deptTreeListVo = new DeptTreeListVo();
        BeanUtils.copyProperties(deptList , deptTreeListVo);
        deptTreeListVo.setChildrenDeptList(deptTreeList);
        return deptTreeListVo;
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
