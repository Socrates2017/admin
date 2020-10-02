package com.zrzhen.admin.module.os.controller;

import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.service.ArticleService;
import com.zrzhen.admin.module.os.rsp.News;
import com.zrzhen.admin.module.os.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {

    @Autowired
    IndexService indexService;


    @Autowired
    ArticleService articleService;

    @GetMapping("")
    public String index(Model model) {

        News news = indexService.getLastNews();
        model.addAttribute("companyNews", news.getCompanyNews());
        model.addAttribute("industryNews", news.getIndustryNews());
        model.addAttribute("banners", news.getBanners());

        return "index";
    }

    @GetMapping("/1")
    public String index1(Model model) {

        News news = indexService.getLastNews();
        model.addAttribute("companyNews", news.getCompanyNews());
        model.addAttribute("industryNews", news.getIndustryNews());
        model.addAttribute("banners", news.getBanners());

        return "index1";
    }

    @GetMapping("product")
    public String product() {
        return "product";
    }

    @GetMapping("applets")
    public String applets() {
        return "applets";
    }

    @GetMapping("scrm")
    public String scrm() {
        return "scrm";
    }

    @GetMapping("aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }

    @GetMapping("businessWechat")
    public String businessWechat() {
        return "businessWechat";
    }

    @GetMapping("cooperation")
    public String cooperation() {
        return "cooperation";
    }

    @GetMapping("news")
    public String news() {
        return "news";
    }

    @GetMapping("recruit")
    public String recruit() {
        return "recruit";
    }

    @GetMapping("solution")
    public String solution() {
        return "solution";
    }

    @GetMapping("publicfoot")
    public String publicfoot() {
        return "publicfoot";
    }

    @GetMapping("publichead")
    public String publichead() {
        return "publichead";
    }


    /**
     * 文章详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/article/{id}")
    public String id(@PathVariable Long id, Model model) {
        Article article = articleService.getById(id);
        String out = "admin/article";
        if (article == null) {
            out = "404";
        } else {
            model.addAttribute("article", article);
        }
        return out;
    }
}
