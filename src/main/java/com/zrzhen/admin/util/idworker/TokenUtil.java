package com.zrzhen.admin.util.idworker;


import com.zrzhen.admin.util.AesUtil;

public class TokenUtil {
    private static String tokenKey = "Ninuo202009";

    public static String genId() {
        long id = GenLong19Util.genLong19();
        String token = AesUtil.encrypt(String.valueOf(id), tokenKey);
        //String token = MD5Util.getMD5(String.valueOf(id));
        return token;
    }


}
