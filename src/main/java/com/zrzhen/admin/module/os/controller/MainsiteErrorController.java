package com.zrzhen.admin.module.os.controller;

import com.zrzhen.admin.module.os.rsp.News;
import com.zrzhen.admin.module.os.service.IndexService;
import com.zrzhen.admin.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenanlian
 */
@Controller
public class MainsiteErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private static final String INDEX_PAGE = "index";
    private static final String PAGE_403 = "403";

    private static final String ERROR_PATH_REST = "/errorRest";

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/403")
    public String notAuth(Model model) {

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        String method = request.getMethod();
        if (!"get".equalsIgnoreCase(method)) {
            return "redirect:" + ERROR_PATH_REST + "?statusCode=" + statusCode;
        }

        response.setStatus(403);

        return PAGE_403;

    }

    @RequestMapping(value = ERROR_PATH)
    public String handleError(Model model) {

        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        String method = request.getMethod();
        if (!"get".equalsIgnoreCase(method)) {
            return "redirect:" + ERROR_PATH_REST + "?statusCode=" + statusCode;
        }

        response.setStatus(200);
        News news = indexService.getLastNews();
        model.addAttribute("companyNews", news.getCompanyNews());
        model.addAttribute("industryNews", news.getIndustryNews());
        model.addAttribute("banners", news.getBanners());


        if (statusCode == 401) {
            return INDEX_PAGE;
        } else if (statusCode == 404) {
            return INDEX_PAGE;
        } else if (statusCode == 403) {
            return PAGE_403;
        } else {
            return INDEX_PAGE;
        }
    }

    @RequestMapping(value = ERROR_PATH_REST)
    @ResponseBody
    public RestResult handleErrorRest(Integer statusCode) {
        //获取statusCode:401,404,500
        if (statusCode == 401) {
            return RestResult.buildFailWithMsg("无权限");
        } else if (statusCode == 404) {
            return RestResult.buildFailWithMsg("请检查链接是否正确");
        } else if (statusCode == 403) {
            return RestResult.buildFailWithMsg("账户未在登录状态");
        } else {
            return RestResult.buildFailWithMsg("账户未在登录状态");
        }
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
