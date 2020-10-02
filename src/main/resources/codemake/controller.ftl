package com.nino.officialsite.module.${module}.controller;

import com.nino.chargeserver.handler.Log;
import com.nino.officialsite.module.${module}.param.${classname}BaseAddParam;
import com.nino.officialsite.module.${module}.param.${classname}BaseUpdateParam;
import com.nino.officialsite.module.${module}.param.${classname}BasePageParam;
import com.nino.officialsite.module.${module}.po.${classname};
import com.nino.officialsite.module.${module}.service.${classname}BaseService;
import com.nino.chargeserver.result.Page;
import com.nino.chargeserver.result.PageParam;
import com.nino.chargeserver.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * ${comments}
 *
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@Api(tags = {"${comments}"})
@RequestMapping(value = "/v1/${classname}")
public class ${classname}BaseController {

    @Autowired
    ${classname}BaseService ${classnameLowCase}BaseService;


    @Log("添加${comments}")
    @ApiOperation(value = "添加一个${comments}")
    @PostMapping("/add")
    public ResponseResult<${classname}> add(@RequestHeader(name = "userId", required = false) Long userId,
                                            @Valid @RequestBody ${classname}BaseAddParam add${classname}Param) throws Exception {
        return ${classnameLowCase}BaseService.add(userId, add${classname}Param);
    }


    @Log("更新${comments}")
    @ApiOperation(value = "更新一个${comments}")
    @PostMapping("/update")
    public ResponseResult<${classname}> update(@RequestHeader(name = "userId", required = false) Long userId,
                                               @Valid @RequestBody ${classname}BaseUpdateParam update${classname}Param) throws Exception {
        return ${classnameLowCase}BaseService.update(userId, update${classname}Param);
    }


    @Log("分页查询${comments}")
    @ApiOperation(value = "分页查询${comments}")
    @PostMapping("/page")
    public Page<${classname}> page(@RequestHeader(name = "userId", required = false) Long userId,
                                   @Valid @RequestBody PageParam<${classname}BasePageParam> ${classnameLowCase}BasePageParam) {

        return ${classnameLowCase}BaseService.get${classname}ByPage(${classnameLowCase}BasePageParam);
    }

}
