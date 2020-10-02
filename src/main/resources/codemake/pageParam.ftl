package com.nino.officialsite.module.${module}.param;

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
public class ${classname}BasePageParam implements Serializable{
	private static final long serialVersionUID = 1L;

    <#list pkList as column>
	<#if column.attrname =='merchantId'>
    <#else>
    //${column.comments}
    private ${column.attrType} ${column.attrname};
     </#if>
    </#list>
	
	<#list columns as column>
    //${column.comments}
    private ${column.attrType} ${column.attrname};
	</#list>


}
