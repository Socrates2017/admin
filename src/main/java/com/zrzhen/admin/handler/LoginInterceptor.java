package com.zrzhen.admin.handler;


import com.zrzhen.admin.result.RestResult;
import com.zrzhen.admin.result.RestResultEnum;
import com.zrzhen.admin.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenanlian
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean out = false;

        //todo
        RestResult result = RestResult.buildSuccess();
        if (result != null && result.getCode() == RestResultEnum.SUCCESS.getCode()) {
            out = true;
        }

        if (!out) {
            ResponseUtil.outJsonEntity(response, result);
        }
        return out;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


}
