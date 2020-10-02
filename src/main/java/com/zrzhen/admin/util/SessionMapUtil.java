package com.zrzhen.admin.util;

import com.zrzhen.admin.module.admin.Constant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenanlian
 */
public class SessionMapUtil {

    public static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap();

    public static int tokenMaxAge = 60 * 60 * 24;

    public static Session getSession(String token) {
        Session session = SessionMapUtil.sessions.get(token);
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public static String getToken(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.getCookie(request, "token");
        if (StringUtils.isBlank(token)) {
            token = SessionUtil.genToken();
            /*添加token给cookie*/
            CookieUtil.addCookie(response, "token", token, tokenMaxAge);
        }
        return token;
    }

    public static String getTokenByRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, "token");
    }

    public static void deleteTokenInResponse(HttpServletResponse response) {
        CookieUtil.removeCookie(response, "token");
        CookieUtil.removeCookie(response, String.valueOf(Constant.syscode));
    }

    public static Session deleteToken(String token) {
        return sessions.remove(token);
    }
}
