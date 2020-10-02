package com.zrzhen.admin.module.admin.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章分类
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/23
 */
public class ArticleCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID
    private Long id;

    //表名
    private String name;
    //描述
    private String description;
    //创建者
    @JsonIgnore
    private Long creator;
    //更新者
    @JsonIgnore
    private Long updater;
    //
    @JsonIgnore
    private Date createTime;
    //
    @JsonIgnore
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
