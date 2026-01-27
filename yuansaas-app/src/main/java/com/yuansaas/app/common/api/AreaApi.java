package com.yuansaas.app.common.api;

import com.yuansaas.app.common.service.AreaCodeService;
import com.yuansaas.app.common.vo.AreaDataVo;
import com.yuansaas.core.response.ResponseBuilder;
import com.yuansaas.core.response.ResponseModel;
import com.yuansaas.user.auth.security.annotations.SecurityAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 地区代码管理api
 *
 * @author LXZ 2026/1/20 16:46
 */
@RestController
@RequestMapping(value = "area")
@RequiredArgsConstructor
public class AreaApi {

    private final AreaCodeService areaCodeService;

//    @RequestMapping(value = "into" , method = RequestMethod.GET)
//    @SecurityAuth
//    public ResponseEntity<ResponseModel<Boolean>> into (@RequestParam(name = "level") Integer level,
//                                                        @RequestParam(name = "pageNo") Integer pageNo,
//                                                        @RequestParam(name = "pageSize") Integer pageSize){
//         areaCodeService.into(level , pageNo , pageSize);
//         return ResponseBuilder.okResponse();
//    }

    /**
     * 获取省列表
     * @param name 地区名字
     * @author LXZ 2026/1/20  18:02
     */
    @RequestMapping(value = "/provinces" , method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<List<AreaDataVo>>> getProvinces(@RequestParam(name = "name" , required = false) String name){
        return ResponseBuilder.okResponse(areaCodeService.getProvinces(name));
    }

    /**
     * 根据省获取市列表
     * @param code 地区code
     * @param name 地区名字
     * @author LXZ 2026/1/20  18:02
     */
    @RequestMapping(value = "/provinces/{code}/cities" , method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<List<AreaDataVo>>> getCities(@PathVariable(value = "code") Long code,
                                                               @RequestParam(name = "name" , required = false) String name){
        return ResponseBuilder.okResponse(areaCodeService.getSublevelByCodeAndName(code, name));
    }

    /**
     * 根据市获取县列表
     * @param code 地区code
     * @param name 地区名字
     * @author LXZ 2026/1/20  18:02
     */
    @RequestMapping(value = "/cities/{code}/districts" , method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<List<AreaDataVo>>> getDistricts(@PathVariable(value = "code") Long code,
                                                               @RequestParam(name = "name" , required = false) String name){
        return ResponseBuilder.okResponse(areaCodeService.getSublevelByCodeAndName(code, name));
    }
    /**
     * 根据县获取城镇列表
     * @param code 地区code
     * @param name 地区名字
     * @author LXZ 2026/1/20  18:02
     */
    @RequestMapping(value = "/districts/{code}/town" , method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<List<AreaDataVo>>> getTown(@PathVariable(value = "code") Long code,
                                                                         @RequestParam(name = "name" , required = false) String name){
        return ResponseBuilder.okResponse(areaCodeService.getSublevelByCodeAndName(code, name));
    }

    /**
     * 根据城镇获取乡村列表
     * @param code 地区code
     * @param name 地区名字
     * @author LXZ 2026/1/20  18:02
     */
    @RequestMapping(value = "/town/{code}/burg" , method = RequestMethod.GET)
    @SecurityAuth
    public ResponseEntity<ResponseModel<List<AreaDataVo>>> getBurg(@PathVariable(value = "code") Long code,
                                                                         @RequestParam(name = "name" , required = false) String name){
        return ResponseBuilder.okResponse(areaCodeService.getSublevelByCodeAndName(code, name));
    }


}
