package com.zrzhen.admin.module.admin.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章标签关联
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/23
 */
public class ArticleTag implements Serializable {
    private static final long serialVersionUID = 1L;

    //文章id
    private Long article;
    //
    private String tag;

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

    public Long getArticle() {
        return article;
    }

    public void setArticle(Long article) {
        this.article = article;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
