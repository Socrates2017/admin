package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.ArticleTag;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * 文章标签关联
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/23
 */
public interface ArticleTagMapper {

    @InsertProvider(type = ArticleTagProvider.class, method = "insertSelective")
    int insertSelective(ArticleTag articleTag);

    @UpdateProvider(type = ArticleTagProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ArticleTag articleTag);


    @Select({
            "select",
            "article,",
            "tag,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `article_tag`",
            "where article = #{article,jdbcType=BIGINT}",
            "and tag = #{tag,jdbcType=VARCHAR}"
    })
    @Results(id = "all", value = {
            @Result(column = "article", property = "article", jdbcType = JdbcType.BIGINT),
            @Result(column = "tag", property = "tag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "creator", property = "creator", jdbcType = JdbcType.BIGINT),
            @Result(column = "updater", property = "updater", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    ArticleTag selectByPrimaryKey(
            Long article,
            String tag
    );

}
