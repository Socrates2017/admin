package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.ArticleTag;
import org.apache.ibatis.jdbc.SQL;

public class ArticleTagProvider {

    public String insertSelective(ArticleTag articleTag) {
        SQL sql = new SQL();
        sql.INSERT_INTO("`article_tag`");

        if (articleTag.getArticle() != null) {
            sql.VALUES("`article`", "#{article,jdbcType=BIGINT}");
        }
        if (articleTag.getTag() != null) {
            sql.VALUES("`tag`", "#{tag,jdbcType=VARCHAR}");
        }

        if (articleTag.getCreator() != null) {
            sql.VALUES("`creator`", "#{creator,jdbcType=BIGINT}");
        }
        if (articleTag.getUpdater() != null) {
            sql.VALUES("`updater`", "#{updater,jdbcType=BIGINT}");
        }
        if (articleTag.getCreateTime() != null) {
            sql.VALUES("`create_time`", "#{createTime,jdbcType=TIMESTAMP}");
        }
        if (articleTag.getUpdateTime() != null) {
            sql.VALUES("`update_time`", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(ArticleTag articleTag) {
        SQL sql = new SQL();
        sql.UPDATE("`article_tag`");

        if (articleTag.getCreator() != null) {
            sql.SET("`creator` = #{creator,jdbcType=BIGINT}");
        }
        if (articleTag.getUpdater() != null) {
            sql.SET("`updater` = #{updater,jdbcType=BIGINT}");
        }
        if (articleTag.getCreateTime() != null) {
            sql.SET("`create_time` = #{createTime,jdbcType=TIMESTAMP}");
        }
        if (articleTag.getUpdateTime() != null) {
            sql.SET("`update_time` = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("`article` = #{article,jdbcType=BIGINT}");
        sql.WHERE("`tag` = #{tag,jdbcType=VARCHAR}");

        return sql.toString();
    }

}