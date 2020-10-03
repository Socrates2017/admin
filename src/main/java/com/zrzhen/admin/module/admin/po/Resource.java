package com.zrzhen.admin.module.admin.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源
 *
 * @author chenanlian
 * @email a@zrzhen.com
 * @date 2020/10/03
 */
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;

    //上级id (顶级为0)
    private Long pid;
    //资源uri
    private String uri;
    //资源编码
    private String code;
    //资源名称；
    private String name;
    //描述
    private String description;
    //是否是菜单, 0：否；1：是
    private Integer isMenu;
    //是否在面包屑中隐藏；0否；1：是
    private Integer hideInBread;
    //是否在菜单中隐藏；0为否；1为是。
    private Integer hideInMenu;
    //图标
    private String icon;
    //排序
    private Integer sort;
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getHideInBread() {
        return hideInBread;
    }

    public void setHideInBread(Integer hideInBread) {
        this.hideInBread = hideInBread;
    }

    public Integer getHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(Integer hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
