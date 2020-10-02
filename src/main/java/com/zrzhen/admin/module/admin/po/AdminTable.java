package com.zrzhen.admin.module.admin.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台管理可操作
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/22
 */
public class AdminTable implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //表名
    private String tableName;

    @JsonIgnore
    private Long creator;

    @JsonIgnore
    private Long updater;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    public AdminTable() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AdminTable{" +
                "tableName='" + tableName + '\'' +
                ", creator=" + creator +
                ", updater=" + updater +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
