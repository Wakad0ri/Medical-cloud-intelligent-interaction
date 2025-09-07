package com.medical.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    public String upload(byte[] bytes, String fileName){

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try{
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));
        } catch (Exception e){
            throw new RuntimeException("上传失败");
        } finally {
            ossClient.shutdown();
        }

        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint.replace("https://", ""))
                .append("/")
                .append(fileName);

        log.info("上传成功：{}", stringBuilder);
        return stringBuilder.toString();

    }
}
