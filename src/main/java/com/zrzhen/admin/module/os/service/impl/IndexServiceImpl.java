package com.zrzhen.admin.module.os.service.impl;

import com.zrzhen.admin.module.admin.dao.ArticleMapper;
import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.req.ArticleReq;
import com.zrzhen.admin.module.os.constant.Cache;
import com.zrzhen.admin.module.os.rsp.News;
import com.zrzhen.admin.module.os.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public News getLastNews() {

        News news = new News();

        List<Article> companyNews = (List<Article>) Cache.get("companyNews");
        if (companyNews == null) {
            ArticleReq articleReq = new ArticleReq();
            articleReq.setCategory(1L);
            companyNews = articleMapper.pageSelective(articleReq, 0, 5, "id desc");
            Cache.put("companyNews", companyNews);
        }

        List<Article> industryNews = (List<Article>) Cache.get("industryNews");
        if (industryNews == null) {
            ArticleReq articleReq2 = new ArticleReq();
            articleReq2.setCategory(2L);
            industryNews = articleMapper.pageSelective(articleReq2, 0, 5, "id desc");
            Cache.put("industryNews", industryNews);
        }

        List<Article> banners = (List<Article>) Cache.get("banners");

        if (banners == null) {
            ArticleReq articleReq = new ArticleReq();
            articleReq.setCategory(3L);
            banners = articleMapper.pageSelective(articleReq, 0, 5, "sort");
            Cache.put("banners", banners);
        }

        news.setBanners(banners);
        news.setCompanyNews(companyNews);
        news.setIndustryNews(industryNews);
        return news;
    }

    @Override
    public void delNeWsCache() {
        Cache.del("companyNews");
        Cache.del("industryNews");
        Cache.del("banners");
    }
}
