package com.nino.officialsite.module.${module}.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ${classname} implements Serializable{
	private static final long serialVersionUID = 1L;

    <#list pkList as column>
    //${column.comments}
    private ${column.attrType} ${column.attrname};
    </#list>
	
	<#list columns as column>
	<#if column.attrname =='version' || column.attrname =='creator' || column.attrname =='updater'|| column.attrname =='createTime'|| column.attrname =='updateTime'>
    //${column.comments}
    @JsonIgnore
    private ${column.attrType} ${column.attrname};
    <#else>
    //${column.comments}
    private ${column.attrType} ${column.attrname};
     </#if>
	</#list>


}
