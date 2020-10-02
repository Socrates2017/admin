package com.zrzhen.admin.handler;


import com.zrzhen.admin.module.admin.service.UserService;
import com.zrzhen.admin.result.RestResult;
import com.zrzhen.admin.result.RestResultEnum;
import com.zrzhen.admin.util.ContentTypeEnum;
import com.zrzhen.admin.util.ResponseUtil;
import org.apache.tomcat.util.http.MimeHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;


public class AdminInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AdminInterceptor.class);


    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();

        log.info("{}:{}", request.getMethod(), uri);

        boolean out = false;

        RestResult result = userService.loginCheck();
        out = true;
        if (result != null && result.getCode() == RestResultEnum.SUCCESS.getCode()) {
            out = true;
        }

        if (!out) {
            String contentType = request.getHeader("content-type");
            ContentTypeEnum contentTypeEnum = ContentTypeEnum.getTypeByTypePre(contentType);
            if (contentTypeEnum != null && !ContentTypeEnum.isJson(contentTypeEnum)) {
                ResponseUtil.outJsonEntity(response, result);
            } else{
                request.getRequestDispatcher("/403").forward(request, response);
            }
        }
        return out;

    }


    /**
     * 修改header信息，key-value键值对儿加入到header中
     *
     * @param request
     * @param key
     * @param value
     */
    private void reflectSetparam(HttpServletRequest request, String key, String value) {
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders) headers.get(o1);
            o2.addValue(key).setString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
