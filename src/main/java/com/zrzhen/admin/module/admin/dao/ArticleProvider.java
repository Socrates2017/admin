package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.req.ArticleReq;
import org.apache.ibatis.jdbc.SQL;

public class ArticleProvider {

    public String insertSelective(Article article) {
        SQL sql = new SQL();
        sql.INSERT_INTO("`article`");

        sql.VALUES("`id`", "#{id,jdbcType=BIGINT}");

        if (article.getTitle() != null) {
            sql.VALUES("`title`", "#{title,jdbcType=VARCHAR}");
        }
        if (article.getAuthor() != null) {
            sql.VALUES("`author`", "#{author,jdbcType=VARCHAR}");
        }
        if (article.getSummary() != null) {
            sql.VALUES("`summary`", "#{summary,jdbcType=VARCHAR}");
        }
        if (article.getCover() != null) {
            sql.VALUES("`cover`", "#{cover,jdbcType=VARCHAR}");
        }
        if (article.getCoverAlt() != null) {
            sql.VALUES("`cover_alt`", "#{coverAlt,jdbcType=VARCHAR}");
        }
        if (article.getCoverDirect() != null) {
            sql.VALUES("`cover_direct`", "#{coverDirect,jdbcType=VARCHAR}");
        }
        if (article.getContent() != null) {
            sql.VALUES("`content`", "#{content,jdbcType=LONGVARCHAR}");
        }
        if (article.getStatus() != null) {
            sql.VALUES("`status`", "#{status,jdbcType=TINYINT}");
        }
        if (article.getCategory() != null) {
            sql.VALUES("`category`", "#{category,jdbcType=BIGINT}");
        }
        if (article.getSort() != null) {
            sql.VALUES("`sort`", "#{sort,jdbcType=INTEGER}");
        }
        if (article.getIsTop() != null) {
            sql.VALUES("`is_top`", "#{isTop,jdbcType=TINYINT}");
        }
        if (article.getCreator() != null) {
            sql.VALUES("`creator`", "#{creator,jdbcType=BIGINT}");
        }
        if (article.getUpdater() != null) {
            sql.VALUES("`updater`", "#{updater,jdbcType=BIGINT}");
        }
        if (article.getCreateTime() != null) {
            sql.VALUES("`create_time`", "#{createTime,jdbcType=TIMESTAMP}");
        }
        if (article.getUpdateTime() != null) {
            sql.VALUES("`update_time`", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Article article) {
        SQL sql = new SQL();
        sql.UPDATE("`article`");

        if (article.getTitle() != null) {
            sql.SET("`title` = #{title,jdbcType=VARCHAR}");
        }
        if (article.getAuthor() != null) {
            sql.SET("`author` = #{author,jdbcType=VARCHAR}");
        }
        if (article.getSummary() != null) {
            sql.SET("`summary` = #{summary,jdbcType=VARCHAR}");
        }
        if (article.getCover() != null) {
            sql.SET("`cover` = #{cover,jdbcType=VARCHAR}");
        }
        if (article.getCoverAlt() != null) {
            sql.SET("`cover_alt` = #{coverAlt,jdbcType=VARCHAR}");
        }
        if (article.getCoverDirect() != null) {
            sql.SET("`cover_direct` = #{coverDirect,jdbcType=VARCHAR}");
        }
        if (article.getContent() != null) {
            sql.SET("`content` = #{content,jdbcType=LONGVARCHAR}");
        }
        if (article.getStatus() != null) {
            sql.SET("`status` = #{status,jdbcType=TINYINT}");
        }
        if (article.getCategory() != null) {
            sql.SET("`category` = #{category,jdbcType=BIGINT}");
        }
        if (article.getSort() != null) {
            sql.SET("`sort` = #{sort,jdbcType=INTEGER}");
        }
        if (article.getIsTop() != null) {
            sql.SET("`is_top` = #{isTop,jdbcType=TINYINT}");
        }
        if (article.getCreator() != null) {
            sql.SET("`creator` = #{creator,jdbcType=BIGINT}");
        }
        if (article.getUpdater() != null) {
            sql.SET("`updater` = #{updater,jdbcType=BIGINT}");
        }
        if (article.getCreateTime() != null) {
            sql.SET("`create_time` = #{createTime,jdbcType=TIMESTAMP}");
        }
        if (article.getUpdateTime() != null) {
            sql.SET("`update_time` = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("`id` = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }


    public String countSelective(ArticleReq article) {
        SQL sql = new SQL();
        sql.SELECT("count(*)");
        sql.FROM("`article`");

        if (article.getId() != null) {
            sql.WHERE("`id` = #{article.id,jdbcType=BIGINT}");
        }

        if (article.getTitle() != null) {
            sql.WHERE("`title` = #{article.title,jdbcType=VARCHAR}");
        }
        if (article.getAuthor() != null) {
            sql.WHERE("`author` = #{article.author,jdbcType=VARCHAR}");
        }
        if (article.getSummary() != null) {
            sql.WHERE("`summary` = #{article.summary,jdbcType=VARCHAR}");
        }
        if (article.getCover() != null) {
            sql.WHERE("`cover` = #{article.cover,jdbcType=VARCHAR}");
        }
        if (article.getContent() != null) {
            sql.WHERE("`content` = #{article.content,jdbcType=LONGVARCHAR}");
        }
        if (article.getStatus() != null) {
            sql.WHERE("`status` = #{article.status,jdbcType=TINYINT}");
        }
        if (article.getCategory() != null) {
            sql.WHERE("`category` = #{article.category,jdbcType=BIGINT}");
        }
        if (article.getSort() != null) {
            sql.WHERE("`sort` = #{article.sort,jdbcType=INTEGER}");
        }
        if (article.getIsTop() != null) {
            sql.WHERE("`is_top` = #{article.isTop,jdbcType=TINYINT}");
        }
        if (article.getCreator() != null) {
            sql.WHERE("`creator` = #{article.creator,jdbcType=BIGINT}");
        }
        if (article.getUpdater() != null) {
            sql.WHERE("`updater` = #{article.updater,jdbcType=BIGINT}");
        }
        if (article.getCreateTimeStart() != null) {
            sql.WHERE("`create_time` > #{article.createTimeStart,jdbcType=TIMESTAMP}");
        }

        if (article.getCreateTimeEnd() != null) {
            sql.WHERE("`create_time` < #{article.createTimeEnd,jdbcType=TIMESTAMP}");
        }

        if (article.getUpdateTimeStart() != null) {
            sql.WHERE("`update_time` > #{article.updateTimeStart,jdbcType=TIMESTAMP}");
        }

        if (article.getUpdateTimeEnd() != null) {
            sql.WHERE("`update_time` < #{article.updateTimeEnd,jdbcType=TIMESTAMP}");
        }

        return sql.toString();
    }

    public String pageSelective(ArticleReq article, Integer offset, Integer pageSize, String orderBy) {
        SQL sql = new SQL();
        sql.SELECT("id,title,author,summary,cover,cover_alt,cover_direct,content,status,category,sort,is_top,creator,updater,create_time,update_time");


        sql.FROM("`article`");


        if (article.getId() != null) {
            sql.WHERE("`id` = #{article.id,jdbcType=BIGINT}");
        }

        if (article.getTitle() != null) {
            sql.WHERE("`title` = #{article.title,jdbcType=VARCHAR}");
        }
        if (article.getAuthor() != null) {
            sql.WHERE("`author` = #{article.author,jdbcType=VARCHAR}");
        }
        if (article.getSummary() != null) {
            sql.WHERE("`summary` = #{article.summary,jdbcType=VARCHAR}");
        }
        if (article.getCover() != null) {
            sql.WHERE("`cover` = #{article.cover,jdbcType=VARCHAR}");
        }
        if (article.getContent() != null) {
            sql.WHERE("`content` = #{article.content,jdbcType=LONGVARCHAR}");
        }
        if (article.getStatus() != null) {
            sql.WHERE("`status` = #{article.status,jdbcType=TINYINT}");
        }
        if (article.getCategory() != null) {
            sql.WHERE("`category` = #{article.category,jdbcType=BIGINT}");
        }
        if (article.getSort() != null) {
            sql.WHERE("`sort` = #{article.sort,jdbcType=INTEGER}");
        }
        if (article.getIsTop() != null) {
            sql.WHERE("`is_top` = #{article.isTop,jdbcType=TINYINT}");
        }
        if (article.getCreator() != null) {
            sql.WHERE("`creator` = #{article.creator,jdbcType=BIGINT}");
        }
        if (article.getUpdater() != null) {
            sql.WHERE("`updater` = #{article.updater,jdbcType=BIGINT}");
        }
        if (article.getCreateTimeStart() != null) {
            sql.WHERE("`create_time` > #{article.createTimeStart,jdbcType=TIMESTAMP}");
        }

        if (article.getCreateTimeEnd() != null) {
            sql.WHERE("`create_time` < #{article.createTimeEnd,jdbcType=TIMESTAMP}");
        }

        if (article.getUpdateTimeStart() != null) {
            sql.WHERE("`update_time` > #{article.updateTimeStart,jdbcType=TIMESTAMP}");
        }

        if (article.getUpdateTimeEnd() != null) {
            sql.WHERE("`update_time` < #{article.updateTimeEnd,jdbcType=TIMESTAMP}");
        }


        String sqlStr = sql.toString();
        if (orderBy != null) {
            sqlStr = sqlStr + " order by " + orderBy;
        }
        sqlStr = sqlStr + " limit " + offset + "," + pageSize;
        return sqlStr;
    }

}