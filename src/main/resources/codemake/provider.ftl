package com.zrzhen.admin.module.${module}.dao;

import com.zrzhen.admin.module.${module}.po.${classname};
import com.zrzhen.admin.module.${module}.param.${classname}BasePageParam;
import org.apache.ibatis.jdbc.SQL;

public class ${classname}BaseProvider {

    public String insertSelective(${classname} ${classnameLowCase}) {
        SQL sql = new SQL();
        sql.INSERT_INTO("`${tableName}`");

        <#list pkList as column>
        if (${classnameLowCase}.get${column.attrName}() != null) {
            sql.VALUES("`${column.columnName}`", "<#noparse>#{</#noparse>${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
        }
        </#list>

        <#list columns as column>
        if (${classnameLowCase}.get${column.attrName}() != null) {
            sql.VALUES("`${column.columnName}`", "<#noparse>#{</#noparse>${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
        }
        </#list>
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(${classname} ${classnameLowCase}) {
        SQL sql = new SQL();
        sql.UPDATE("`${tableName}`");

        <#list columns as column>
        if (${classnameLowCase}.get${column.attrName}() != null) {
            sql.SET("`${column.columnName}` = <#noparse>#{</#noparse>${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
        }
        </#list>

        <#list pkList as column>
        sql.WHERE("`${column.columnName}` = <#noparse>#{</#noparse>${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
        </#list>

        return sql.toString();
    }

    public String countSelective(${classname}BasePageParam ${classnameLowCase}Page) {
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        sql.FROM("`${tableName}`");

       <#list pkList as column>
       if (${classnameLowCase}Page.get${column.attrName}() != null) {
           sql.WHERE("`${column.columnName}` = <#noparse>#{</#noparse>${classnameLowCase}Page.${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
       }
       </#list>

       <#list columns as column>
       if (${classnameLowCase}Page.get${column.attrName}() != null) {
           sql.WHERE("`${column.columnName}` = <#noparse>#{</#noparse>${classnameLowCase}Page.${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
       }
       </#list>

        return sql.toString();
    }

    public String pageSelective(${classname}BasePageParam ${classnameLowCase}Page, Integer rowStart, Integer pageSize) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("`${tableName}`");


       <#list pkList as column>
       if (${classnameLowCase}Page.get${column.attrName}() != null) {
           sql.WHERE("`${column.columnName}` = <#noparse>#{</#noparse>${classnameLowCase}Page.${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
       }
       </#list>

       <#list columns as column>
       if (${classnameLowCase}Page.get${column.attrName}() != null) {
           sql.WHERE("`${column.columnName}` = <#noparse>#{</#noparse>${classnameLowCase}Page.${column.attrname},jdbcType=${column.dataTypeUpcase}<#noparse>}</#noparse>");
       }
       </#list>


        String sqlStr = sql.toString();
        sqlStr = sqlStr + " limit " + rowStart + "," + pageSize;
        return sqlStr;
    }
}