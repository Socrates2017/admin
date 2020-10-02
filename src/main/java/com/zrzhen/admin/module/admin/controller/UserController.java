package com.zrzhen.admin.module.admin.controller;


import com.zrzhen.admin.module.admin.Constant;
import com.zrzhen.admin.module.admin.po.User;
import com.zrzhen.admin.module.admin.po.UserEnum;
import com.zrzhen.admin.module.admin.req.LoginReq;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenanlian
 */
@RestController
@RequestMapping("/api/v1/admin/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;


    @Autowired
    UserService userService;

    @GetMapping("/login/check")
    public RestResult loginCheck() {
        return userService.loginCheck();
    }

    @PostMapping("/login")
    public RestResult login(@RequestBody LoginReq loginReq) {

        String token = SessionMapUtil.getTokenByRequest(request);

        if (StringUtils.isBlank(token)) {
            log.warn("token 为空");
            return RestResult.build(RestResultEnum.TOKEN_NULL_CLIENT);
        }

        Session session = SessionMapUtil.sessions.get(token);

        if (session == null) {
            /*服务端session缺失，需要重新生成*/
            /*删除cookie中的token*/
            SessionMapUtil.deleteTokenInResponse(response);
            return RestResult.build(RestResultEnum.TOKEN_EXPIRED_SERVER);
        }

        String authCode = loginReq.getAuthCode();

        if (StringUtils.isBlank(authCode)) {

            return RestResult.buildFailWithMsg("验证码为空");
        }


        String authCode1 = session.getAuthCode();

        if (!authCode1.equalsIgnoreCase(authCode)) {
            /*不一致*/
            log.debug("验证码不一致,authCode:{}；codeId:{}", authCode, authCode1);
            return RestResult.buildFailWithMsg("验证码错误");
        }

        Long userId = session.getUserId();
        if (userId != null) {
            /*添加用户id*/
            CookieUtil.addCookie(response, String.valueOf(Constant.syscode), String.valueOf(userId));
            User user = UserEnum.getUserBy(userId);
            return RestResult.buildSuccess(user);

        }

        //进行登录

        String name = loginReq.getName();
        String pwd = loginReq.getPwd();

        if (StringUtils.isBlank(name)) {
            return RestResult.buildFailWithMsg("用户名为空");
        }

        if (StringUtils.isBlank(pwd)) {
            return RestResult.buildFailWithMsg("密码");
        }

        User user = UserEnum.getUserByNameAndPwd(name, pwd);

        if (user == null) {
            user = UserEnum.getUserByName(name);
            if (user == null) {
                return RestResult.buildFailWithMsg("用户名错误");
            } else {
                return RestResult.buildFailWithMsg("密码错误");
            }
        }

        Long id = user.getId();

        session.setUserId(id);

        /*删除cookie中的token*/
        //CookieUtil.removeCookie(response, "sessionid");
        /*添加sessionid给cookie*/
        CookieUtil.addCookie(response, "token", token, SessionMapUtil.tokenMaxAge);
        /*添加用户id*/
        CookieUtil.addCookie(response, String.valueOf(Constant.syscode), String.valueOf(id), SessionMapUtil.tokenMaxAge);
        return RestResult.buildSuccess(user);
    }


    @PostMapping("/logout")
    public RestResult logout() {

        String token = SessionMapUtil.getTokenByRequest(request);

        if (StringUtils.isBlank(token)) {
            log.warn("token 为空");
            return RestResult.build(RestResultEnum.TOKEN_NULL_CLIENT);
        }

        Session session = SessionMapUtil.sessions.get(token);

        if (session == null) {
            /*服务端session缺失，需要重新生成*/
            /*删除cookie中的token*/
            SessionMapUtil.deleteTokenInResponse(response);
            return RestResult.buildSuccess("账户未在登录状态");
        }


        //删除服务端sessionid
        SessionMapUtil.deleteToken(token);

        SessionMapUtil.deleteTokenInResponse(response);

        return RestResult.buildSuccessWithMsg("退出成功");


    }

}
