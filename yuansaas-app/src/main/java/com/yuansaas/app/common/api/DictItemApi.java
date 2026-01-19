package com.yuansaas.app.common.api;

import com.yuansaas.app.common.params.*;
import com.yuansaas.app.common.service.DictItemService;
import com.yuansaas.app.common.vo.SysDictDataVo;
import com.yuansaas.core.page.RPage;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 字典项配置
 *
 * @author LXZ 2025/11/17 16:00
 */
@RestController
@RequestMapping(value = "/dict/item")
@RequiredArgsConstructor
public class DictItemApi {

    private final DictItemService dictItemService;

    /**
     * 创建字典项数据
     * @param saveDictItemParam 创建字典相关参数
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @SecurityAuth
    public ResponseEntity<ResponseModel<Boolean>> createDict(@RequestBody @Validated SaveDictItemParam saveDictItemParam) {
        return ResponseBuilder.okResponse(dictItemService.createDict(saveDictItemParam));
    }

    /**
     * 编辑字典项数据
     * @param updateDictItemParam 编辑字典相关参数
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @SecurityAuth
    public ResponseEntity<ResponseModel<Boolean>> updateDict(@RequestBody @Validated UpdateDictItemParam updateDictItemParam) {
        return ResponseBuilder.okResponse(dictItemService.updateDict(updateDictItemParam));
    }

    /**
     * 根据字典项id删除字典数据
     * @param id 字典id
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @SecurityAuth
    public ResponseEntity<ResponseModel<Boolean>> deleteDict(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(dictItemService.deleteDict(id));
    }

    /**
     * 查询字典项分页
     *
     * @param findDictItemParam 字典id
     * @author lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<RPage<SysDictDataVo>>> findByPage(FindDictItemParam findDictItemParam) {
        return ResponseBuilder.okResponse(dictItemService.findByPage(findDictItemParam));
    }


    /**
     * 查询字典项
     *
     * @param dictCode 字典code
     * @param dictLabel  字典key
     * @author lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<SysDictDataVo>> findByDictCodeAndDictLabel(@RequestParam(value = "dictCode") String dictCode ,
                                                               @RequestParam(value = "dictLabel") String dictLabel) {
        return ResponseBuilder.okResponse(dictItemService.findByDictCodeAndDictLabel(dictCode ,dictLabel));
    }


    /**
     * 禁用字典项
     * @param id 字典id
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/disable/{id}",method = RequestMethod.PUT)
    @SecurityAuth
    public ResponseEntity<ResponseModel<Boolean>> disable(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(dictItemService.lock(id));
    }

    /**
     * 启用字典项
     * @param id 字典id
     * @author  lxz 2025/11/16 14:35
     */
    @RequestMapping(value = "/enable/{id}",method = RequestMethod.PUT)
    @SecurityAuth
    public ResponseEntity<ResponseModel<Boolean>> enable(@PathVariable(value = "id") Long id) {
        return ResponseBuilder.okResponse(dictItemService.lock(id));
    }

}
