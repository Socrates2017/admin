package com.zrzhen.admin.module.admin.service.impl;

import com.zrzhen.admin.module.admin.dao.ArticleCategoryMapper;
import com.zrzhen.admin.module.admin.dao.ArticleMapper;
import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.po.ArticleCategory;
import com.zrzhen.admin.module.admin.req.ArticleReq;
import com.zrzhen.admin.module.admin.rsp.ArticleRsp;
import com.zrzhen.admin.module.admin.service.ArticleService;
import com.zrzhen.admin.module.os.service.IndexService;
import com.zrzhen.admin.result.RestPage;
import com.zrzhen.admin.result.RestPageParam;
import com.zrzhen.admin.result.RestResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    IndexService indexService;

    @Override
    public RestResult add(Article article) {

        int row = articleMapper.insertSelective(article);
        if (row > 0) {
            indexService.delNeWsCache();
            return RestResult.buildSuccess(article.getId());
        } else {
            return RestResult.buildFailWithMsg("新增失败");
        }

    }

    @Override
    public RestResult update(Article article) {
        int row = articleMapper.updateByPrimaryKeySelective(article);
        if (row > 0) {
            return RestResult.buildSuccess(article.getId());
        } else {
            return RestResult.buildFailWithMsg("更新失败");
        }
    }

    @Override
    public RestResult delete(Long id) {
        int row = articleMapper.deleteById(id);
        if (row > 0) {
            indexService.delNeWsCache();
            return RestResult.buildSuccess(id);
        } else {
            return RestResult.buildFailWithMsg("删除失败");
        }
    }

    @Override
    public Article getById(Long id) {

        return articleMapper.selectByPk(id);
    }

    @Override
    public RestResult<List<ArticleCategory>> getAllArticleCategory() {

        return RestResult.buildSuccess(articleCategoryMapper.getAll());
    }

    @Override
    public RestPage<ArticleRsp> page(RestPageParam<ArticleReq> pageParam) {
        int currentPage = pageParam.getCurrentPage();
        int pageSize = pageParam.getPageSize();
        ArticleReq article = pageParam.getT();

        Integer offset = (currentPage - 1) * pageSize;

        Long totalRows = articleMapper.countSelective(article);//查询出总条数

        Integer totalPages = 0;

        List<ArticleRsp> rsps = new ArrayList<>();
        if (totalRows > 0) {
            totalPages = (int) Math.ceil(totalRows / (pageSize > 0 ? pageSize : 20));
            List<Article> rows = articleMapper.pageSelective(article, offset, pageSize, "id desc");

            if (rows != null && rows.size() > 0) {

                for (Article article1 : rows) {
                    ArticleRsp articleRsp = new ArticleRsp();
                    BeanUtils.copyProperties(article1, articleRsp);
                    String categoryName = articleCategoryMapper.getNameById(article1.getCategory());
                    articleRsp.setCategoryName(categoryName);
                    rsps.add(articleRsp);
                }
            }
        }
        return new RestPage(totalRows, totalPages, currentPage, pageSize, rsps);
    }
}
