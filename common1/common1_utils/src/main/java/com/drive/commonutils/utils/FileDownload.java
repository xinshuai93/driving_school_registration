package com.drive.commonutils.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Slf4j
@Component
public class FileDownload {
    public static HttpServletResponse download(String path, HttpServletResponse response){
//        try {
//            // path是指欲下载的文件的路径。
//            File file = new File(path);
//            // 取得文件名。
//            String filename = file.getName();
//            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
//
//            // 以流的形式下载文件。
//            InputStream fis = new BufferedInputStream(new FileInputStream(path));
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return response;
        try {
            Date date = new Date();
            XWPFDocument xwpfDocument = new XWPFDocument(Files.newInputStream(Paths.get(path)));
            String exportName = new SimpleDateFormat("yyyyMMddHHmm").format(date);
            response.setCharacterEncoding("UTF-8");
            response.reset();
            response.setHeader("Access-Control-Expose-Headers","Content-Disposition");//暴露Content-Disposition
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(exportName+".docx", "UTF-8"));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ServletOutputStream out = response.getOutputStream();
            xwpfDocument.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
