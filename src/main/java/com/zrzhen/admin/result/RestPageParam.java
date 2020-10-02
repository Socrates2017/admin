package com.zrzhen.admin.result;

public class RestPageParam<T> {
    private static final long serialVersionUID = 1L;

    private int currentPage;//当前页
    private int pageSize;//一页包含多少条记录

    private T t;

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

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}