package com.zrzhen.admin.module.admin.service;

import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.po.ArticleCategory;
import com.zrzhen.admin.module.admin.req.ArticleReq;
import com.zrzhen.admin.module.admin.rsp.ArticleRsp;
import com.zrzhen.admin.result.RestPage;
import com.zrzhen.admin.result.RestPageParam;
import com.zrzhen.admin.result.RestResult;

import java.util.List;

public interface ArticleService {

    RestResult add(Article article);

    RestResult update(Article article);

    RestResult delete(Long id);

    Article getById(Long id);

    RestResult<List<ArticleCategory>> getAllArticleCategory();

    RestPage<ArticleRsp> page(RestPageParam<ArticleReq> pageParam) ;

}
