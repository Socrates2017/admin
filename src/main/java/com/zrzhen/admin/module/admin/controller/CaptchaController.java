package com.zrzhen.admin.module.admin.controller;

import com.zrzhen.admin.module.admin.req.CheckAuthCodeReq;
import com.zrzhen.admin.result.RestResult;
import com.zrzhen.admin.result.RestResultEnum;
import com.zrzhen.admin.util.Session;
import com.zrzhen.admin.util.SessionMapUtil;
import org.apache.commons.lang3.StringUtils;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.*;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码生成
 *
 * @author ChenAnlian
 */
@Controller
public class CaptchaController {

    private static Logger log = LoggerFactory.getLogger(CaptchaController.class);


    private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
    private static Random random = new Random();

    static {
        // cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setColorFactory(new ColorFactory() {

            @Override
            public Color getColor(int arg0) {
                int[] c = new int[3];
                int i = random.nextInt(c.length);
                for (int fi = 0; fi < c.length; fi++) {
                    if (fi == i) {
                        c[fi] = random.nextInt(71);
                    } else {
                        c[fi] = random.nextInt(256);
                    }
                }
                return new Color(c[0], c[1], c[2]);
            }
        });
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("345678abcdefhigmnqrstuvyABCDEFGHGLMNQRSTUVY");
        wf.setMaxLength(4);
        wf.setMinLength(4);
        cs.setWordFactory(wf);
    }

    @Autowired
    private HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @RequestMapping(value = "/api/v1/authCode/img", method = RequestMethod.GET)
    public void getCrimg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (random.nextInt(5)) {
            case 0:
                cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                break;
            case 1:
                cs.setFilterFactory(new MarbleRippleFilterFactory());
                break;
            case 2:
                cs.setFilterFactory(new DoubleRippleFilterFactory());
                break;
            case 3:
                cs.setFilterFactory(new WobbleRippleFilterFactory());
                break;
            case 4:
                cs.setFilterFactory(new DiffuseRippleFilterFactory());
                break;
        }
        setResponseHeaders(response);
        String token = SessionMapUtil.getToken(request, response);
        OutputStream os = response.getOutputStream();
        String authCode = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);

        Session session = SessionMapUtil.getSession(token);
        session.setAuthCode(authCode);
        SessionMapUtil.sessions.put(token, session);
    }

    protected void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }

    /**
     * 验证码是否一致
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/authCode/check", method = RequestMethod.POST)
    @ResponseBody
    public RestResult checkAuthCode(@RequestBody CheckAuthCodeReq checkAuthCodeReq) {
        String authCode = checkAuthCodeReq.getAuthCode();
        if (StringUtils.isBlank(authCode)) {
            log.debug("验证码参数缺失");
            return RestResult.buildFailWithMsg("验证码参数缺失");
        }

        String token = SessionMapUtil.getToken(request, response);
        Session session = SessionMapUtil.getSession(token);

        String authCode1 = session.getAuthCode();

        if (StringUtils.isBlank(authCode1)) {
            log.error("服务端codeId缺失,authCode:{}", authCode);
            return RestResult.buildFailWithMsg("服务端验证码为空");
        }

        if (!authCode1.equalsIgnoreCase(authCode)) {
            /*不一致*/
            log.debug("验证码不一致,authCode:{}；codeId:{}", authCode, authCode1);
            return RestResult.build(RestResultEnum.AUTH_CODE_WRONG);
        }

        /*一致*/
        return RestResult.buildSuccess();

    }
}