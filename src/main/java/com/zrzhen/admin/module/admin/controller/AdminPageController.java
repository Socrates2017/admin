package com.zrzhen.admin.module.admin.controller;

import com.zrzhen.admin.module.admin.po.Article;
import com.zrzhen.admin.module.admin.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminPageController {

    private static Logger log = LoggerFactory.getLogger(AdminPageController.class);

    @Autowired
    ArticleService articleService;

    /**
     * 管理后台主页
     *
     * @return
     */
    @GetMapping("")
    public String admin() {
        return "admin/admin";
    }


    /**
     * 管理后台主页
     *
     * @return
     */
    @GetMapping("upload")
    public String upload() {
        return "admin/upload";
    }

    /**
     * 文章中心
     * @return
     */
    @GetMapping("/article/center")
    public String articleCenter() {
        return "admin/articleCenter";
    }

    /**
     * 文章详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/article/info/{id}")
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

    /**
     * 文章更新
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/article/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Article article = articleService.getById(id);
        String out = "admin/articleUpdate";
        if (article == null) {
            out = "";
        } else {
            model.addAttribute("article", article);
        }
        return out;
    }

    @GetMapping("/article/add")
    public String articleAdd() {
        return "admin/articleAdd";
    }


}
