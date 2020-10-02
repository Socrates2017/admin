package com.zrzhen.admin.util;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.zrzhen.admin.result.RestResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    private static final Log log = LogFactory.getLog(ResponseUtil.class);



    public static void outJsonBody(HttpServletResponse response, String jsonBody) {

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            if (StringUtils.isNotBlank(jsonBody)) {
                response.setHeader("Content-Length", String.valueOf(jsonBody.getBytes("utf-8").length));
            }
            writer.print(jsonBody);
            response.flushBuffer();
            // 无需flush和close，Tomcat会处理
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void outJsonBody(HttpServletResponse response, int status, String jsonBody) {
        response.setStatus(status);
        outJsonBody(response, jsonBody);
    }

    public static void outJsonEntity(HttpServletResponse response, RestResult body) {
        String jsonBody = JsonUtil.entity2Json(body);
        outJsonBody(response, jsonBody);
    }

    public static void outJsonEntity(HttpServletResponse response, int status, RestResult body) {

        String jsonBody = JsonUtil.entity2Json(body);
        response.setStatus(status);
        outJsonBody(response, jsonBody);

    }

}
