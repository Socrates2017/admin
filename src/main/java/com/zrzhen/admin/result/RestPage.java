package com.zrzhen.admin.result;

import java.util.List;

public class RestPage<T> {
    private static final long serialVersionUID = 1L;


    private long totalRows; //总记录数
    private int totalPages; //总页数

    private int currentPage;//当前页
    private int pageSize;//一页包含多少条记录

    private List<T> rows;

    public RestPage(long totalRows, int totalPages, int currentPage, int pageSize, List<T> rows) {
        this.totalRows = totalRows;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.rows = rows;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}