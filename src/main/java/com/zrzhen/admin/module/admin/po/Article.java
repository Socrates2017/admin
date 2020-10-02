package com.zrzhen.admin.module.admin.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/09/28
 */
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID
    private Long id;

    //标题
    private String title;
    //作者
    private String author;
    //摘要
    private String summary;
    //封面图片
    private String cover;
    //
    private String coverAlt;
    //封面点击时跳转；尤其是轮播图类型时有用
    private String coverDirect;
    //内容
    private String content;
    //状态，0草稿；1发布；2私密
    private Integer status;
    //分类
    private Long category;
    //排序
    private Integer sort;
    //是否置顶；0否；1是；
    private Integer isTop;
    //创建者
    private Long creator;
    //更新者
    private Long updater;
    //
    private Date createTime;
    //
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverAlt() {
        return coverAlt;
    }

    public void setCoverAlt(String coverAlt) {
        this.coverAlt = coverAlt;
    }

    public String getCoverDirect() {
        return coverDirect;
    }

    public void setCoverDirect(String coverDirect) {
        this.coverDirect = coverDirect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
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
