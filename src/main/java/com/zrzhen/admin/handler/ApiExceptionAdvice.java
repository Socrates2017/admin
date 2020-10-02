package com.zrzhen.admin.handler;

import com.zrzhen.admin.result.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器增强，是controller的增强，和ExceptionHandler一起用来做全局异常
 * 可以配置全局异常处理
 *
 * @author chenanlian
 */
@ControllerAdvice
@ResponseBody
public class ApiExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionAdvice.class);


    @ExceptionHandler(Exception.class)
    public RestResult ExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return RestResult.buildFailWithMsg(e.getMessage());
    }


}