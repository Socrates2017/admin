package com.zrzhen.admin.module.admin.rsp;

import com.zrzhen.admin.module.admin.po.Article;

public class ArticleRsp extends Article {

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
