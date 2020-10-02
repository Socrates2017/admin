package com.nino.officialsite.module.${module}.service.impl;

import com.nino.chargeserver.result.ResponseResult;
import com.nino.chargeserver.util.idmaker.IdUtil;
import com.nino.officialsite.module.${module}.dao.${classname}BaseMapper;
import com.nino.officialsite.module.${module}.param.${classname}BaseAddParam;
import com.nino.officialsite.module.${module}.param.${classname}BaseUpdateParam;
import com.nino.officialsite.module.${module}.param.${classname}BasePageParam;
import com.nino.officialsite.module.${module}.po.${classname};
import com.nino.officialsite.module.${module}.service.${classname}BaseService;
import com.nino.chargeserver.result.Page;
import com.nino.chargeserver.result.PageParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
public class ${classname}BaseServiceImpl implements ${classname}BaseService {

    @Autowired
    ${classname}BaseMapper ${classnameLowCase}BaseMapper;

    @Override
    public ResponseResult<${classname}> add(Long userId, ${classname}BaseAddParam add${classname}Param) throws Exception{
        ${classname} po = new ${classname}();
        <#if isRelationTable ==0>
        <#list pkList as column>
        po.set${column.attrName}(IdUtil.genId());
        </#list>
        </#if>
        BeanUtils.copyProperties(po, add${classname}Param);
        po.setCreator(userId);
        ${classnameLowCase}BaseMapper.insertSelective(po);
        return ResponseResult.buildSuccess(po);
    }

    @Override
    public ResponseResult<${classname}> update(Long userId, ${classname}BaseUpdateParam update${classname}Param) throws Exception{
        ${classname} po = new ${classname}();
        BeanUtils.copyProperties(po, update${classname}Param);
        po.setUpdator(userId);
        ${classnameLowCase}BaseMapper.updateByPrimaryKeySelective(po);
        return ResponseResult.buildSuccess(po);

    }

    @Override
    public ResponseResult<${classname}> info(
                <#list pkList as column>
                <#if column_has_next >
                ${column.attrType} ${column.attrname},
                <#else>
                ${column.attrType} ${column.attrname}
                </#if>
                </#list>
                ) {

            ${classname} po = ${classnameLowCase}BaseMapper.selectByPrimaryKey(
                            <#list pkList as column>
                            <#if column_has_next >
                            ${column.attrname},
                            <#else>
                            ${column.attrname}
                            </#if>
                            </#list>
            );
            return ResponseResult.buildSuccess(po);
    }

    @Override
    public Page<${classname}> get${classname}ByPage( PageParam<${classname}BasePageParam> ${classnameLowCase}BasePageParam) {
        Integer pageSize = ${classnameLowCase}BasePageParam.getPageSize();
        Integer pageNo = ${classnameLowCase}BasePageParam.getPageNo();
        Integer rowStart = (pageNo - 1) * pageSize;
        Integer totalRows = ${classnameLowCase}BaseMapper.countSelective(${classnameLowCase}BasePageParam.getT());
        Integer totalPages = 0;
        List<${classname}> rows;
        if (totalRows > 0) {
            totalPages = (int) Math.ceil(totalRows / (pageSize > 0 ? pageSize : 20));
            rows = ${classnameLowCase}BaseMapper.pageSelective(${classnameLowCase}BasePageParam.getT(), rowStart, pageSize);
        } else {
            rows = new ArrayList<>();
        }
        return new Page(totalRows, totalPages, pageNo, pageSize, rows);

    }
}
