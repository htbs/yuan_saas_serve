package com.yuansaas.user.dept.api;

import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import com.yuansaas.user.dept.vo.DeptListVo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 *
 * 部门管理Api
 *
 * @author LXZ 2025/10/16 15:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
public class DeptApi {

    /**
     * 列表查询
     */
    @GetMapping("/list")
    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<Page<DeptListVo>>> list() {
        return ResponseBuilder.okResponse();
    }

    /**
     * 新增部门
     */
    @PostMapping("/save")
    @SecurityAuth(authenticated = false)
    public ResponseModel<Boolean> save() {
        return ResponseBuilder.okResponse();
    }
    /**
     * 修改部门
     */
    @PutMapping("/update")
    @SecurityAuth(authenticated = false)
    public ResponseModel<Boolean> update() {
        return ResponseBuilder.okResponse();
    }
    /**
     * 删除部门
     */
    @GetMapping("/delete/{id}")
    @SecurityAuth(authenticated = false)
    public ResponseModel<Boolean> delete() {
        return ResponseBuilder.okResponse();
    }
    /**
     * 部门详情
     */
    @GetMapping("/{id}")
    @SecurityAuth(authenticated = false)
    public ResponseEntity<ResponseModel<DeptListVo>> getById(@RequestPart("id") Long id ) {
        return ResponseBuilder.okResponse();
    }
}
