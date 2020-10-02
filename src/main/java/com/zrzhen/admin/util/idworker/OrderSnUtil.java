package com.zrzhen.admin.util.idworker;

import com.zrzhen.admin.util.TimeUtil;

import java.util.Random;


public class OrderSnUtil {

    public static String gen32Sn() {
        return gen32Sn(null);
    }

    /**
     * 生成不重复的32位订单号
     *
     * @param postFix12 后缀标识，不能超过12位，如超过，则进行截取
     * @return
     */
    public static String gen32Sn(String postFix12) {
        if (postFix12 == null) {
            postFix12 = "";
        } else {
            postFix12 = postFix12.trim();
        }

        //不重复序列19位
        long long19 = GenLong19Util.timeNoEpoch(GenLong19Util.genLong19());
        //转成字符串
        String long19Str = String.valueOf(long19);
        //秒级时间戳
        long time = Long.valueOf(long19Str.substring(0, 13));
        String timeStr = TimeUtil.timestamp2Str(time, "yyyyMMddHHmmss");
        //不重复序列的后部分。不重复序列时间戳转日期后共20位=14+6
        String long15postFix = long19Str.substring(13);
        int postFix12Size = postFix12.length();
        //后缀位12位
        if (postFix12Size > 12) {
            //取前12位
            postFix12 = postFix12.substring(0, 12);
        } else if (postFix12Size < 12) {
            postFix12 = postFix12 + getRandomString(12 - postFix12Size);
        } else {
            //等于12
            postFix12 = getRandomString(12);
        }

        return timeStr + long15postFix + postFix12;
    }

    /**
     * 随机生成指定长度字母字符串
     *
     * @param length 字符串长度
     * @return
     */
    private static String getRandomString(int length) {
        if (length == 0) {
            return "";
        }
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
