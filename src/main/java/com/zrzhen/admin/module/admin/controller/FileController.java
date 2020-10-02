package com.zrzhen.admin.module.admin.controller;

import com.zrzhen.admin.module.ueditor.UeditorUtil;
import com.zrzhen.admin.result.RestResult;
import com.zrzhen.admin.util.TimeUtil;
import com.zrzhen.admin.util.TxCloudUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author chenanlian
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private static Logger log = LoggerFactory.getLogger(FileController.class);


    @Autowired
    HttpServletRequest request;

    @Value("${file.upload.domain}")
    private String webUploadDomain;

//    @Value("${txcloud.upload.dir}")
//    private String txcloudUploadDir;


    @RequestMapping("/upload")
    public RestResult upload(@RequestParam("file") MultipartFile file) {

        String webUploadDir = UeditorUtil.getUploadDir();
        if (!webUploadDir.endsWith(File.separator)) {
            webUploadDir += File.separator;
        }


        String fileNameWhole = file.getOriginalFilename();
        Integer indexOfDot = fileNameWhole.lastIndexOf(".");
        String suffixName = "";
        String fileName = "";
        if (indexOfDot > 0) {
            suffixName = fileNameWhole.substring(indexOfDot);
            fileName = fileNameWhole.substring(0, indexOfDot);
        } else {
            fileName = fileNameWhole;
        }

        String date = TimeUtil.date2Str(new Date(), "yyyyMMddHHmmssS");

        String newFileName = fileName + "-DATE-" + date + suffixName;
        String destPath = webUploadDir + newFileName;

        File dest = new File(destPath);

        try {
            file.transferTo(dest);

//            String filePath = txcloudUploadDir + newFileName;
//            TxCloudUtil.upLoadByFile(dest, filePath);
//
//            FileUtils.forceDeleteOnExit(dest);


            String url = webUploadDomain + newFileName;
            return RestResult.buildSuccess(url);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return RestResult.buildFailWithMsg(e.getMessage());
        }
    }


}
