package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.ArticleCategory;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 文章分类
 * 
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/23
 */
public interface ArticleCategoryMapper {

    @InsertProvider(type= ArticleCategoryProvider.class, method="insertSelective")
    int insertSelective(ArticleCategory articleCategory);

    @UpdateProvider(type = ArticleCategoryProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ArticleCategory articleCategory);


    @Select({
            "select",
            "id,",
            "name,",
            "description,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `article_category`",
            "where id = #{id,jdbcType=BIGINT}",
    })
    @Results(id = "all", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "creator", property = "creator", jdbcType = JdbcType.BIGINT),
            @Result(column = "updater", property = "updater", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    ArticleCategory getById(
            Long id
    );

    @Select({
            "select",
            "name",
            "from `article_category`",
            "where id = #{id,jdbcType=BIGINT}",
    })
    String getNameById(
            Long id
    );

    @Select({
            "select",
            "id,",
            "name,",
            "description,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `article_category`",
            "order by id",
    })
    @ResultMap("all")
    List<ArticleCategory> getAll(
    );
}
