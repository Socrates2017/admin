package com.zrzhen.admin.result;

public enum RestResultEnum implements RestResultBase {


    SUCCESS(1, "success"),
    FAIL(0, "fail"),


    //服务端失效
    TOKEN_EXPIRED_SERVER(10001, "登录已经失效"),
    //客户端失效
    TOKEN_NULL_CLIENT(10002, "登录已经失效"),

    AUTH_CODE_WRONG(10005, "验证码错误"),


    ;

    private int code;

    private String msg;

    public static RestResultEnum getEntityByCode(int code) {
        for (RestResultEnum entity : RestResultEnum.values()) {
            if (code == entity.getCode()) {
                return entity;
            }
        }
        return null;
    }

    public static String getMsgByCode(int code) {
        RestResultEnum entity = getEntityByCode(code);
        if (entity != null) {
            return entity.getMsg();
        }
        return null;
    }


    RestResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }


    @Override
    public String getMsg() {
        return msg;
    }

}
