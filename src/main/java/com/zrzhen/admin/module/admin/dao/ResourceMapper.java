package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.Resource;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * 资源
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/10/03
 */
public interface ResourceMapper {

    @InsertProvider(type = ResourceProvider.class, method = "insertSelective")
    int insertSelective(Resource resource);

    @UpdateProvider(type = ResourceProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Resource resource);


    @Select({
            "select",
            "id,",
            "pid,",
            "uri,",
            "code,",
            "name,",
            "description,",
            "is_menu,",
            "hide_in_bread,",
            "hide_in_menu,",
            "icon,",
            "sort,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `resource`",
            "where id = #{id,jdbcType=BIGINT}",
    })
    @Results(id = "all", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "pid", property = "pid", jdbcType = JdbcType.BIGINT),
            @Result(column = "uri", property = "uri", jdbcType = JdbcType.VARCHAR),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_menu", property = "isMenu", jdbcType = JdbcType.TINYINT),
            @Result(column = "hide_in_bread", property = "hideInBread", jdbcType = JdbcType.TINYINT),
            @Result(column = "hide_in_menu", property = "hideInMenu", jdbcType = JdbcType.TINYINT),
            @Result(column = "icon", property = "icon", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sort", property = "sort", jdbcType = JdbcType.INTEGER),
            @Result(column = "creator", property = "creator", jdbcType = JdbcType.BIGINT),
            @Result(column = "updater", property = "updater", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    Resource selectByPrimaryKey(
            Long id
    );


}
