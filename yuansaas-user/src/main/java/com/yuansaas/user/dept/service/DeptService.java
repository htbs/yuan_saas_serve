package com.yuansaas.user.dept.service;

import com.yuansaas.core.page.RPage;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.dept.params.FindDeptParam;
import com.yuansaas.user.dept.params.SaveDeptParam;
import com.yuansaas.user.dept.params.UpdateDeptParam;
import com.yuansaas.user.dept.vo.DeptListVo;
import com.yuansaas.user.dept.vo.DeptTreeListVo;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 部门管理
 *
 * @author LXZ 2025/10/17 11:58
 */
public interface DeptService {

    /**
     * 列表查询
     */
    List<DeptTreeListVo> list(FindDeptParam findDeptParam);

    /**
     * 新增部门
     */
    Boolean save(SaveDeptParam saveDeptParam);
    /**
     * 修改部门
     */
    Boolean update(UpdateDeptParam updateDeptParam);
    /**
     * 删除部门
     */
    Boolean delete(Long id );
    /**
     * 部门详情
     */
    DeptListVo getById(Long id );
}
