package com.zrzhen.admin.module.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenanlian
 */
@Component(value = "SessionConstant")
public class Constant {

    public static String db;

    @Value("${constant.db}")
    public void setDb(String db) {
        Constant.db = db;
    }


    public static String domain;

    @Value("${Constant.domain}")
    public void setDomain(String domain) {
        Constant.domain = domain;
    }

    public static Integer syscode = 100;


}
