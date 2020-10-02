package com.zrzhen.admin.module.admin.service.impl;

import com.zrzhen.admin.module.admin.Constant;
import com.zrzhen.admin.module.admin.po.User;
import com.zrzhen.admin.module.admin.po.UserEnum;
import com.zrzhen.admin.module.admin.service.UserService;
import com.zrzhen.admin.result.RestResult;
import com.zrzhen.admin.result.RestResultEnum;
import com.zrzhen.admin.util.CookieUtil;
import com.zrzhen.admin.util.Session;
import com.zrzhen.admin.util.SessionMapUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Override
    public RestResult loginCheck() {
        String token = SessionMapUtil.getTokenByRequest(request);

        if (StringUtils.isBlank(token)) {
            log.warn("token 为空");
            return RestResult.build(RestResultEnum.TOKEN_NULL_CLIENT);
        }

        Session session = SessionMapUtil.sessions.get(token);

        if (session == null) {
            /*服务端session缺失，需要重新生成*/
            /*删除cookie中的token*/
            CookieUtil.removeCookie(response, "token");
            return RestResult.build(RestResultEnum.TOKEN_EXPIRED_SERVER);
        }

        Long userid = session.getUserId();

        if (userid == null) {
            return RestResult.build(RestResultEnum.TOKEN_EXPIRED_SERVER);
        }

        /*添加用户id*/
        CookieUtil.addCookie(response, String.valueOf(Constant.syscode), String.valueOf(userid));
        User user = UserEnum.getUserBy(userid);
        return RestResult.buildSuccess(user);
    }
}