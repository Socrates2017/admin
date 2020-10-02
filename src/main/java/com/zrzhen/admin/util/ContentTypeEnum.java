package com.zrzhen.admin.util;

import org.apache.commons.lang3.StringUtils;

import java.net.URLConnection;

/**
 * @author chenanlian
 * <p>
 * 内容类型枚举
 */

public enum ContentTypeEnum {

    /**
     * 纯文本格式
     */
    TEXT("text/plain; charset=UTF-8"),

    /**
     * HTML格式
     */
    HTML("text/html; charset=UTF-8", "html"),

    /**
     * JSON格式
     */
    JSON("application/json; charset=UTF-8"),


    /**
     * JS
     */
    JS("application/javascript; charset=UTF-8", "js"),

    /**
     * CSS
     */
    CSS("text/css; charset=UTF-8", "css"),

    /**
     * 二进制流数据（如常见的文件下载）
     */
    OCTET("application/octet-stream; charset=UTF-8"),

    /**
     * 文件
     */
    FORM_DATA("multipart/form-data;"),

    /**
     * 二进制流数据（如常见的文件下载）
     */
    URL_ENCODED("application/x-www-form-urlencoded; charset=UTF-8"),

    JPG("image/jpeg; charset=UTF-8", "jpg"),

    PNG("image/png;", "png"),

    ICO("image/png; charset=UTF-8", "ico"),

    AUDIO_MP3("audio/mpeg; ", "mp3"),
    ;

    private String type;

    private String suffix;

    ContentTypeEnum(String type) {
        this.type = type;
    }

    ContentTypeEnum(String type, String suffix) {
        this.type = type;
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getTypeBySuffix(String suffix) {

        for (ContentTypeEnum api : ContentTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(suffix, api.getSuffix())) {
                return api.getType();
            }
        }
        return null;
    }

    public static ContentTypeEnum getTypeByTypePre(String typePre) {

        if (typePre ==null){
            return null;
        }

        int index2 = typePre.indexOf(";");
        if (index2 > 0) {
            typePre = typePre.substring(0, index2);
        }

        for (ContentTypeEnum api : ContentTypeEnum.values()) {

            String type = api.getType();
            int index = type.indexOf(";");
            String typePre1 = type.substring(0, index);

            if (StringUtils.equalsIgnoreCase(typePre1, typePre)) {
                return api;
            }
        }
        return null;
    }




    /**
     * 根据文件名来获得媒体类型
     *
     * @param fileName
     * @return
     */
    public static String contentTypeByFileName(String fileName) {
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
        if (StringUtils.isBlank(contentType)) {
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            contentType = ContentTypeEnum.getTypeBySuffix(suffix);
            if (StringUtils.isBlank(contentType)) {
                contentType = ContentTypeEnum.OCTET.getType();
            }
        }
        return contentType;
    }

    public static boolean isStringType(ContentTypeEnum type) {

        if (type == JSON || type == TEXT || type == HTML) {
            return true;
        }
        return false;
    }

    public static boolean isPage(ContentTypeEnum type) {

        if (type == HTML) {
            return true;
        }
        return false;
    }

    public static boolean isJson(ContentTypeEnum type) {

        if (type == JSON) {
            return true;
        }
        return false;
    }


}
