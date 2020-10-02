package com.zrzhen.admin.module.ueditor;

import com.zrzhen.admin.util.TimeUtil;
import com.zrzhen.admin.util.TxCloudUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

@Service
public class UeditorService {

    private static Logger log = LoggerFactory.getLogger(UeditorUtil.class);

    private static String uploadDir = null;

    @Value("${file.upload.domain}")
    private String webUploadDomain;

//    @Value("${txcloud.upload.dir}")
//    private String txcloudUploadDir;


    public String uploadFile(String type, MultipartFile file, String callback) {

        String webUploadDir = UeditorUtil.getUploadDir();
        if (!webUploadDir.endsWith(File.separator)) {
            webUploadDir += File.separator;
        }

        String[] fileAllowType = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};
        String fileOldName = file.getOriginalFilename();

        Integer indexOfDot = fileOldName.lastIndexOf(".");
        String fileName = "";
        String fileExt = "";
        if (indexOfDot > 0) {
            fileExt = fileOldName.substring(indexOfDot);
            fileName = fileOldName.substring(0, indexOfDot);
        } else {
            fileName = fileOldName;
        }

        if (!Arrays.asList(fileAllowType).contains(fileExt) && type.equals("image")) {
            return "不允许的文件类型！";
        }


        String date = TimeUtil.date2Str(new Date(), "yyyyMMddHHmmssS");
        String newFileName = fileName + "-UEDITOR-" + date + fileExt;

        String destPath = webUploadDir + newFileName;
        File dest = new File(destPath);

        String url = "";

        try {

            file.transferTo(dest);

//            String filePath = txcloudUploadDir + newFileName;
//            TxCloudUtil.upLoadByFile(dest, filePath);
//            url = webUploadDomain + filePath;
//
//            FileUtils.forceDeleteOnExit(dest);

            url = webUploadDomain + newFileName;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


        String result = "{\"name\":\"" + newFileName + "\", \"originalName\": \"" + newFileName + "\", \"size\": " + file.getSize() + ", \"state\": \"" + "SUCCESS" + "\", \"type\": \"" + fileExt + "\", \"url\": \"" + url + "\"}";
        result = result.replaceAll("\\\\", "\\\\");


        if (callback != null && callback.equals("fileName")) {
            return url;
        } else if (callback == null) {
            return result;
        } else {
            return "<script>" + callback + "(" + result + ")</script>";
        }
    }

}
