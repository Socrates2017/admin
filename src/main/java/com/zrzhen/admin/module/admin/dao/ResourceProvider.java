package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.po.Resource;
import org.apache.ibatis.jdbc.SQL;

public class ResourceProvider {

    public String insertSelective(Resource resource) {
        SQL sql = new SQL();
        sql.INSERT_INTO("`resource`");

        if (resource.getId() != null) {
            sql.VALUES("`id`", "#{id,jdbcType=BIGINT}");
        }

        if (resource.getPid() != null) {
            sql.VALUES("`pid`", "#{pid,jdbcType=BIGINT}");
        }
        if (resource.getUri() != null) {
            sql.VALUES("`uri`", "#{uri,jdbcType=VARCHAR}");
        }
        if (resource.getCode() != null) {
            sql.VALUES("`code`", "#{code,jdbcType=VARCHAR}");
        }
        if (resource.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        if (resource.getDescription() != null) {
            sql.VALUES("`description`", "#{description,jdbcType=VARCHAR}");
        }
        if (resource.getIsMenu() != null) {
            sql.VALUES("`is_menu`", "#{isMenu,jdbcType=TINYINT}");
        }
        if (resource.getHideInBread() != null) {
            sql.VALUES("`hide_in_bread`", "#{hideInBread,jdbcType=TINYINT}");
        }
        if (resource.getHideInMenu() != null) {
            sql.VALUES("`hide_in_menu`", "#{hideInMenu,jdbcType=TINYINT}");
        }
        if (resource.getIcon() != null) {
            sql.VALUES("`icon`", "#{icon,jdbcType=VARCHAR}");
        }
        if (resource.getSort() != null) {
            sql.VALUES("`sort`", "#{sort,jdbcType=INTEGER}");
        }
        if (resource.getCreator() != null) {
            sql.VALUES("`creator`", "#{creator,jdbcType=BIGINT}");
        }
        if (resource.getUpdater() != null) {
            sql.VALUES("`updater`", "#{updater,jdbcType=BIGINT}");
        }
        if (resource.getCreateTime() != null) {
            sql.VALUES("`create_time`", "#{createTime,jdbcType=TIMESTAMP}");
        }
        if (resource.getUpdateTime() != null) {
            sql.VALUES("`update_time`", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Resource resource) {
        SQL sql = new SQL();
        sql.UPDATE("`resource`");

        if (resource.getPid() != null) {
            sql.SET("`pid` = #{pid,jdbcType=BIGINT}");
        }
        if (resource.getUri() != null) {
            sql.SET("`uri` = #{uri,jdbcType=VARCHAR}");
        }
        if (resource.getCode() != null) {
            sql.SET("`code` = #{code,jdbcType=VARCHAR}");
        }
        if (resource.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        if (resource.getDescription() != null) {
            sql.SET("`description` = #{description,jdbcType=VARCHAR}");
        }
        if (resource.getIsMenu() != null) {
            sql.SET("`is_menu` = #{isMenu,jdbcType=TINYINT}");
        }
        if (resource.getHideInBread() != null) {
            sql.SET("`hide_in_bread` = #{hideInBread,jdbcType=TINYINT}");
        }
        if (resource.getHideInMenu() != null) {
            sql.SET("`hide_in_menu` = #{hideInMenu,jdbcType=TINYINT}");
        }
        if (resource.getIcon() != null) {
            sql.SET("`icon` = #{icon,jdbcType=VARCHAR}");
        }
        if (resource.getSort() != null) {
            sql.SET("`sort` = #{sort,jdbcType=INTEGER}");
        }
        if (resource.getCreator() != null) {
            sql.SET("`creator` = #{creator,jdbcType=BIGINT}");
        }
        if (resource.getUpdater() != null) {
            sql.SET("`updater` = #{updater,jdbcType=BIGINT}");
        }
        if (resource.getCreateTime() != null) {
            sql.SET("`create_time` = #{createTime,jdbcType=TIMESTAMP}");
        }
        if (resource.getUpdateTime() != null) {
            sql.SET("`update_time` = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("`id` = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }


}