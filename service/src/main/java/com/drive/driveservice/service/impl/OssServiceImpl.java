package com.drive.driveservice.service.impl;

import org.joda.time.DateTime;
import com.aliyun.oss.OSSClient;
import com.drive.driveservice.service.OssService;
import com.drive.driveservice.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //工具类获取值
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            //创建OSSClient实例
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            //上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //避免文件名重复以致覆盖掉
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileName = uuid + fileName;

            //获取当前时间创建阿里云里的分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;

            //文件上传至阿里云
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            //把上传之后文件路径返回回来
            //需要把上传到阿里云oss路径手动拼接出来
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            return url;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }
}
