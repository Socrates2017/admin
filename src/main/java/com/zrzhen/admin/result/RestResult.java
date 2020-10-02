package com.zrzhen.admin.result;

import com.zrzhen.admin.util.JsonUtil;

/**
 * @author chenanlian
 */
public class RestResult<T> {

    private int code;

    private String msg;

    private T data;

    public RestResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static RestResult build(RestResultBase restResultBase) {
        return new RestResult(restResultBase.getCode(), restResultBase.getMsg());
    }

    public static <T> RestResult<T> build(RestResultBase restResultBase, T data) {
        return new RestResult<T>(restResultBase.getCode(), restResultBase.getMsg(), data);
    }

    public static RestResult buildWithCode(int code, String msg) {
        return new RestResult(code, msg);
    }

    public static <T> RestResult<T> buildWithCode(int code, String msg, T data) {
        return new RestResult<T>(code, msg, data);
    }


    public static RestResult buildSuccess() {
        return new RestResult(RestResultEnum.SUCCESS.getCode(), RestResultEnum.SUCCESS.getMsg());
    }

    public static <T> RestResult<T> buildSuccess(T data) {
        return new RestResult<T>(RestResultEnum.SUCCESS.getCode(), RestResultEnum.SUCCESS.getMsg(), data);
    }

    public static RestResult buildSuccessWithMsg(String msg) {
        return new RestResult(RestResultEnum.SUCCESS.getCode(), msg);
    }

    public static <T> RestResult<T> buildSuccessWithMsg(String msg, T data) {
        return new RestResult(RestResultEnum.SUCCESS.getCode(), msg, data);
    }

    public static RestResult buildFail() {
        return new RestResult(RestResultEnum.FAIL.getCode(), RestResultEnum.FAIL.getMsg());
    }

    public static <T> RestResult<T> buildFail(T data) {
        return new RestResult<T>(RestResultEnum.FAIL.getCode(), RestResultEnum.FAIL.getMsg(), data);
    }

    public static RestResult buildFailWithMsg(String msg) {
        return new RestResult(RestResultEnum.FAIL.getCode(), msg);
    }

    public static <T> RestResult<T> buildFailWithMsg(String msg, T data) {
        return new RestResult(RestResultEnum.FAIL.getCode(), msg, data);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JsonUtil.entity2Json(this);
    }
}
