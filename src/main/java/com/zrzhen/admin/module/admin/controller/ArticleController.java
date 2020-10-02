package com.zrzhen.admin.module.admin.controller;


import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.req.ArticleReq;
import com.zrzhen.admin.module.admin.rsp.ArticleRsp;
import com.zrzhen.admin.module.admin.service.ArticleService;
import com.zrzhen.admin.result.RestPage;
import com.zrzhen.admin.result.RestPageParam;
import com.zrzhen.admin.result.RestResult;
import com.zrzhen.admin.util.idworker.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenanlian
 */
@RestController
@RequestMapping("/api/v1/admin/article")
public class ArticleController {

    private static Logger log = LoggerFactory.getLogger(ArticleController.class);


    @Autowired
    HttpServletRequest request;

    @Autowired
    ArticleService articleService;


    @PostMapping("/add")
    public RestResult add(@RequestBody Article article) {
        article.setId(IdUtil.getId());
        return articleService.add(article);
    }

    @PostMapping("/update")
    public RestResult update(@RequestBody Article article) {
        //校验权限，todo
        return articleService.update(article);
    }

    @PostMapping("/delete")
    public RestResult delete(@RequestBody Article article) {
        //校验权限，todo
        return articleService.delete(article.getId());
    }

    @GetMapping("/getAllArticleCategory")
    public RestResult getAllArticleCategory() {

        return articleService.getAllArticleCategory();
    }


    @PostMapping("/page")
    public RestResult<RestPage<ArticleRsp>> page(@RequestBody RestPageParam<ArticleReq> pageParam) {

        return RestResult.buildSuccess(articleService.page(pageParam));
    }


}
