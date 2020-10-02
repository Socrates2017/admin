package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.AdminTable;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * 后台管理可操作
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/22
 */
public interface AdminTableMapper {

    @InsertProvider(type = AdminTableProvider.class, method = "insertSelective")
    int insertSelective(AdminTable adminTable);

    @UpdateProvider(type = AdminTableProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AdminTable adminTable);


    @Select({
            "select",
            "table_name,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `admin_table`",
            "where table_name = #{tableName,jdbcType=VARCHAR}",
    })
    @Results(id = "all", value = {
            @Result(column = "table_name", property = "tableName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "creator", property = "creator", jdbcType = JdbcType.BIGINT),
            @Result(column = "updater", property = "updater", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    AdminTable selectByPrimaryKey(
            String tableName
    );


    @Select({
            "select",
            "table_name,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `admin_table` order by create_time",
    })
    @ResultMap("all")
    List<AdminTable> all();

    @Select({
            "select ",
            "*",
            "from ${tableName} ",
            "limit ${start},${row}"
    })
    @ResultType(List.class)
    List<Map> listFromTable(@Param("tableName") String tableName, @Param("start") Integer start, @Param("row") Integer row);

    @SelectProvider(type = AdminTableProvider.class, method = "columns")
    @ResultType(List.class)
    List<Map> columns(String tableName);

    /**
     * 万能查询接口
     *
     * @param sqlStr
     * @return
     */
    @SelectProvider(type = AdminTableProvider.class, method = "execute")
    List<Map<String, Object>> execute(String sqlStr);
}
