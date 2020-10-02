package com.zrzhen.admin.util.idworker;

public class IdUtil {


    /**
     * 生成15位的长整型；超过15位js的解析有精度问题
     * @return
     */
    public static long getId() {
        return GenLong15Util.genLong15();
    }


}
