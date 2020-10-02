package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.ArticleCategory;
import org.apache.ibatis.jdbc.SQL;

public class ArticleCategoryProvider {

    public String insertSelective(ArticleCategory articleCategory) {
        SQL sql = new SQL();
        sql.INSERT_INTO("`article_category`");

        if (articleCategory.getId() != null) {
            sql.VALUES("`id`", "#{id,jdbcType=BIGINT}");
        }

        if (articleCategory.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        if (articleCategory.getDescription() != null) {
            sql.VALUES("`description`", "#{description,jdbcType=VARCHAR}");
        }
        if (articleCategory.getCreator() != null) {
            sql.VALUES("`creator`", "#{creator,jdbcType=BIGINT}");
        }
        if (articleCategory.getUpdater() != null) {
            sql.VALUES("`updater`", "#{updater,jdbcType=BIGINT}");
        }
        if (articleCategory.getCreateTime() != null) {
            sql.VALUES("`create_time`", "#{createTime,jdbcType=TIMESTAMP}");
        }
        if (articleCategory.getUpdateTime() != null) {
            sql.VALUES("`update_time`", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(ArticleCategory articleCategory) {
        SQL sql = new SQL();
        sql.UPDATE("`article_category`");

        if (articleCategory.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        if (articleCategory.getDescription() != null) {
            sql.SET("`description` = #{description,jdbcType=VARCHAR}");
        }
        if (articleCategory.getCreator() != null) {
            sql.SET("`creator` = #{creator,jdbcType=BIGINT}");
        }
        if (articleCategory.getUpdater() != null) {
            sql.SET("`updater` = #{updater,jdbcType=BIGINT}");
        }
        if (articleCategory.getCreateTime() != null) {
            sql.SET("`create_time` = #{createTime,jdbcType=TIMESTAMP}");
        }
        if (articleCategory.getUpdateTime() != null) {
            sql.SET("`update_time` = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("`id` = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }

}