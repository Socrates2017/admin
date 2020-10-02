package com.nino.officialsite.module.${module}.service;

import com.nino.officialsite.module.${module}.param.${classname}BaseAddParam;
import com.nino.officialsite.module.${module}.param.${classname}BaseUpdateParam;
import com.nino.officialsite.module.${module}.param.${classname}BasePageParam;
import com.nino.officialsite.module.${module}.po.${classname};
import com.nino.chargeserver.result.Page;
import com.nino.chargeserver.result.PageParam;
import com.nino.chargeserver.result.ResponseResult;

/**
 * ${comments}
 *
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
public interface ${classname}BaseService {

    ResponseResult<${classname}>  add(Long userId, ${classname}BaseAddParam add${classname}Param) throws Exception;

    ResponseResult<${classname}>  update(Long userId, ${classname}BaseUpdateParam update${classname}Param) throws Exception;

    ResponseResult<${classname}>  info(
                <#list pkList as column>
                <#if column_has_next >
                ${column.attrType} ${column.attrname},
                <#else>
                ${column.attrType} ${column.attrname}
                </#if>
                </#list> );

    Page<${classname}> get${classname}ByPage(PageParam<${classname}BasePageParam> ${classnameLowCase}BasePageParam);


}
