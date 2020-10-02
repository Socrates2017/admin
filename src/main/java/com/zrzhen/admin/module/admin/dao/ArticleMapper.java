package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.req.ArticleReq;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 文章
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/23
 */
public interface ArticleMapper {

    @InsertProvider(type = ArticleProvider.class, method = "insertSelective")
    int insertSelective(Article article);

    @UpdateProvider(type = ArticleProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Article article);

    @Delete({
            "delete from `article`",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteById(Long id);

    @Select({
            "select",
            "id,",
            "title,",
            "author,",
            "summary,",
            "cover,",
            "cover_alt,",
            "cover_direct,",
            "content,",
            "status,",
            "category,",
            "sort,",
            "is_top,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `article`",
            "where id = #{id,jdbcType=BIGINT}",
    })
    @Results(id = "all", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "author", property = "author", jdbcType = JdbcType.VARCHAR),
            @Result(column = "summary", property = "summary", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cover", property = "cover", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cover_alt", property = "coverAlt", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cover_direct", property = "coverDirect", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.TINYINT),
            @Result(column = "category", property = "category", jdbcType = JdbcType.BIGINT),
            @Result(column = "sort", property = "sort", jdbcType = JdbcType.INTEGER),
            @Result(column = "is_top", property = "isTop", jdbcType = JdbcType.TINYINT),
            @Result(column = "creator", property = "creator", jdbcType = JdbcType.BIGINT),
            @Result(column = "updater", property = "updater", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    Article selectByPk(
            Long id
    );

    @ResultMap("all")
    @SelectProvider(type = ArticleProvider.class, method = "pageSelective")
    List<Article> pageSelective(@Param("article") ArticleReq article, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("orderBy") String orderBy);

    @SelectProvider(type = ArticleProvider.class, method = "countSelective")
    Long countSelective(@Param("article") ArticleReq article);


    @Select({
            "select",
            "id,",
            "title,",
            "summary,",
            "cover,",
            "status,",
            "category,",
            "sort,",
            "is_top,",
            "creator,",
            "updater,",
            "create_time,",
            "update_time",
            "from `article`",
            "where category =#{category,jdbcType=BIGINT} and status = #{status,jdbcType=BIGINT}",
            "order by id desc limit 5",
    })
    @ResultMap("all")
    List<Article> getForList(
            Long category,
            Integer status
    );
}
