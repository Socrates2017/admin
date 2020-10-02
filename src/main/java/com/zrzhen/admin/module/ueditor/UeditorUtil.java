package com.zrzhen.admin.module.ueditor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UeditorUtil {

    private static Logger log = LoggerFactory.getLogger(UeditorUtil.class);

    private static String uploadDir = null;


    @Value("${file.upload.dir}")
    public void setueditorUploadDir(String ueditorUploadDir) {
        UeditorUtil.uploadDir = System.getProperty("user.dir") + File.separator + ueditorUploadDir + File.separator;

        File file = new File(UeditorUtil.uploadDir);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getUploadDir() {
        return UeditorUtil.uploadDir;
    }


}
