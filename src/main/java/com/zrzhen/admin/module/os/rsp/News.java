package com.zrzhen.admin.module.os.rsp;

import com.zrzhen.admin.module.admin.po.Article;

import java.util.List;

public class News {

    List<Article> companyNews;

    List<Article> industryNews;

    List<Article> banners;

    public News() {
    }

    public List<Article> getBanners() {
        return banners;
    }

    public void setBanners(List<Article> banners) {
        this.banners = banners;
    }

    public List<Article> getCompanyNews() {
        return companyNews;
    }

    public void setCompanyNews(List<Article> companyNews) {
        this.companyNews = companyNews;
    }

    public List<Article> getIndustryNews() {
        return industryNews;
    }

    public void setIndustryNews(List<Article> industryNews) {
        this.industryNews = industryNews;
    }
}
