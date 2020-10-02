package com.zrzhen.admin.module.admin.req;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author chenanlian
 */
public class AdminReq implements Serializable {

    /**
     * 主键
     */
    private List<Map> pri;

    /**
     * 非主键列
     */
    private List<Map> notPri;

    /**
     * 表名
     */
    private String tableName;

    public List<Map> getPri() {
        return pri;
    }

    public void setPri(List<Map> pri) {
        this.pri = pri;
    }

    public List<Map> getNotPri() {
        return notPri;
    }

    public void setNotPri(List<Map> notPri) {
        this.notPri = notPri;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}