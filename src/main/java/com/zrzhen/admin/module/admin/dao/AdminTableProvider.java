package com.zrzhen.admin.module.admin.dao;

import com.zrzhen.admin.module.admin.Constant;
import com.zrzhen.admin.module.admin.po.AdminTable;
import org.apache.ibatis.jdbc.SQL;

public class AdminTableProvider {

    public String insertSelective(AdminTable adminTable) {
        SQL sql = new SQL();
        sql.INSERT_INTO("`admin_table`");

        if (adminTable.getTableName() != null) {
            sql.VALUES("`table_name`", "#{tableName,jdbcType=VARCHAR}");
        }

        if (adminTable.getCreator() != null) {
            sql.VALUES("`creator`", "#{creator,jdbcType=BIGINT}");
        }
        if (adminTable.getUpdater() != null) {
            sql.VALUES("`updater`", "#{updater,jdbcType=BIGINT}");
        }
        if (adminTable.getCreateTime() != null) {
            sql.VALUES("`create_time`", "#{createTime,jdbcType=TIMESTAMP}");
        }
        if (adminTable.getUpdateTime() != null) {
            sql.VALUES("`update_time`", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(AdminTable adminTable) {
        SQL sql = new SQL();
        sql.UPDATE("`admin_table`");

        if (adminTable.getCreator() != null) {
            sql.SET("`creator` = #{creator,jdbcType=BIGINT}");
        }
        if (adminTable.getUpdater() != null) {
            sql.SET("`updater` = #{updater,jdbcType=BIGINT}");
        }
        if (adminTable.getCreateTime() != null) {
            sql.SET("`create_time` = #{createTime,jdbcType=TIMESTAMP}");
        }
        if (adminTable.getUpdateTime() != null) {
            sql.SET("`update_time` = #{updateTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("`table_name` = #{tableName,jdbcType=VARCHAR}");

        return sql.toString();
    }

    public String execute(String sql) {
        return sql;
    }

    public String columns(String tableName) {
        String sqlStr = "select DISTINCT COLUMN_NAME,DATA_TYPE,COLUMN_KEY from information_schema.COLUMNS where TABLE_SCHEMA='";
        sqlStr += Constant.db;
        sqlStr += "' AND TABLE_NAME = " + "'" + tableName + "'";
        return sqlStr;
    }

}