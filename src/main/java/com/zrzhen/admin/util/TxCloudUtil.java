package com.zrzhen.admin.util;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * TX云上传文件工具类
 */
@Component
public class TxCloudUtil {

    private static Logger log = LoggerFactory.getLogger(TxCloudUtil.class);


    private static String appId = "xxx";
    private static String secretId = "xxx";
    private static String secretKey = "xxx";
    private static String bucket = "xxx";

    private static COSClient cosClient = null;

    @PostConstruct
    public void init() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    public static JSONObject getInfo() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        try {
            // 替换为您的 SecretId
            config.put("SecretId", secretId);
            // 替换为您的 SecretKey
            config.put("SecretKey", secretKey);

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);

            // 换成您的 bucket
            config.put("bucket", bucket);
            // 换成 bucket 所在地区
            config.put("region", "ap-guangzhou");

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的目录，例子：* 或者 doc/* 或者 picture.jpg
            config.put("allowPrefix", "*");

            // 密钥的权限列表。简单上传、表单上传和分片上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[]{
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload",
                    "name/cos:GetService",
                    "name/cos:PutBucket",
                    "name/cos:HeadBucket",
                    "name/cos:GetBucket",
                    "name/cos:DeleteBucket",
                    "name/cos:PutBucketACL",
                    "name/cos:GetBucketACL",
                    "name/cos:PutBucketCORS",
                    "name/cos:GetBucketCORS",
                    "name/cos:DeleteBucketCORS",
                    "name/cos:ListMultipartUploads",
                    "name/cos:HeadObject",
                    "name/cos:GetObject",
                    "name/cos:PutObject",
                    "name/cos:OptionsObject",
                    "name/cos:PostObjectRestore",
                    "name/cos:DeleteObject",
                    "name/cos:DeleteObject",
            };
            config.put("allowActions", allowActions);

            JSONObject credential = CosStsClient.getCredential(config);
            return credential;
        } catch (Exception e) {
            log.error("TxCloudUtil getInfo error!", e);
            return null;
        }
    }


    public static void upLoadFile(String filePath, String key) {
        // 指定要上传的文件
        File localFile = new File(filePath);
        // 指定要上传到的存储桶
        // 指定要上传到 COS 上对象键
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }

    public static void upLoadByFile(File filePath, String key) {
        // 指定要上传的文件
        // 指定要上传到的存储桶
        // 指定要上传到 COS 上对象键
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, filePath);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getInfo());
    }


}
