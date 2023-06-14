package com.drive.commonutils.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Slf4j
@Component
public class FileUpload {
    public static String uploadToLocal(MultipartFile file){
        // 获取文件原本的名字
        String originName = file.getOriginalFilename();
        // 判断文件是否是pdf文件
        Set<String> set = new HashSet<>();
        set.add(".pdf");
        set.add(".doc");
        set.add(".docx");
        // 取出文件的后缀
        int count = 0;
        for(int i = 0; i < originName.length(); i++) {
            if (originName.charAt(i) == '.') {
                count = i;
                break;
            }
        }
        String endName = originName.substring(count); //取出文件类型
        String fileType = originName.substring(count + 1); //文件类型
        if(!set.contains(endName)){
            return new String("上传的文件类型错误,只能上传pdf,doc,docx类型的文件");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        String savePath = System.getProperty("user.dir") + "\\" + "files" +   "\\" + fileType + "\\" + format;
        // 保存文件的文件夹
        File folder = new File(savePath);
        // 判断路径是否存在,不存在则自动创建
        if(!folder.exists()){
            folder.mkdirs();
        }
        String saveName = originName;
        try {
            file.transferTo(new File(folder,saveName));
            String filePath = savePath + "\\" + saveName;
            return new String("文件路径为:" + filePath);
        } catch (IOException e){
            return new String(e.getMessage());
        }
    }
}
