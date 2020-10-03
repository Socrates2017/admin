package com.zrzhen.admin.module.${module}.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
 
@Data
@ApiModel
public class ${classname}BaseAddParam implements Serializable {
	private static final long serialVersionUID = 1L;

    <#if isRelationTable ==1>
    <#list pkList as column>
    //${column.comments}
    @ApiModelProperty(value = "${column.comments}")
    private ${column.attrType} ${column.attrname};
    </#list>
	</#if>
	
	<#list columns as column>
	<#if column.attrname =='version' || column.attrname =='creator' || column.attrname =='updator'|| column.attrname =='createTime'|| column.attrname =='updateTime'>

    <#else>
    //${column.comments}
    @ApiModelProperty(value = "${column.comments}")
    private ${column.attrType} ${column.attrname};
     </#if>
	</#list>

}
