package com.zrzhen.admin.module.${module}.dao;

import com.zrzhen.admin.module.${module}.po.${classname};
import com.zrzhen.admin.module.${module}.param.${classname}BasePageParam;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
public interface ${classname}BaseMapper {

    @InsertProvider(type=${classname}BaseProvider.class, method="insertSelective")
    int insertSelective(${classname} ${classnameLowCase});

    @UpdateProvider(type = ${classname}BaseProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(${classname} ${classnameLowCase});


    @Select({
            "select",
            <#list pkList as column>
            "${column.columnName},",
            </#list>
            <#list columns as column>
            <#if column_has_next>
            "${column.columnName},",
            <#else >
            "${column.columnName}",
            </#if>
            </#list>
            "from `${tableName}`",
            <#list pkList as column>
            <#if column_index = 0>
            "where ${column.columnName} = <#noparse>#{</#noparse>${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>",
            <#elseif column_has_next >
            "and ${column.columnName} = <#noparse>#{</#noparse>${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>",
            <#else >
            "and ${column.columnName} = <#noparse>#{</#noparse>${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>"
            </#if>
            </#list>
    })
    @Results(id = "all", value = {
            <#list pkList as column>
            @Result(column = "${column.columnName}", property = "${column.attrname}", jdbcType = JdbcType.${column.dataTypeUpcase}),
            </#list>
            <#list columns as column>
            <#if column_has_next>
            @Result(column = "${column.columnName}", property = "${column.attrname}", jdbcType = JdbcType.${column.dataTypeUpcase}),
            <#else >
            @Result(column = "${column.columnName}", property = "${column.attrname}", jdbcType = JdbcType.${column.dataTypeUpcase})
            </#if>
            </#list>
    })
    ${classname} selectByPrimaryKey(
            <#list pkList as column>
            <#if column_has_next >
            ${column.attrType} ${column.attrname},
            <#else>
            ${column.attrType} ${column.attrname}
            </#if>
            </#list>
    );

    @ResultMap("all")
    @SelectProvider(type = ${classname}BaseProvider.class, method = "pageSelective")
    List<${classname}> pageSelective(@Param("${classnameLowCase}Page") ${classname}BasePageParam ${classnameLowCase}Page, @Param("rowStart") Integer rowStart, @Param("pageSize") Integer pageSize);

    @SelectProvider(type = ${classname}BaseProvider.class, method = "countSelective")
    Integer countSelective(@Param("${classnameLowCase}Page") ${classname}BasePageParam ${classnameLowCase}Page);

}
