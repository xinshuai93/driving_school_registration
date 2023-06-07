package com.drive.driveservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.drive.driveservice.dto.RecordReportDTO;
import com.drive.driveservice.entity.Test;
import com.drive.driveservice.mapper.TestMapper;
import com.drive.driveservice.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-06-01
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public void reportCheckRecord(RecordReportDTO dto, HttpServletResponse response) {
        Date date = new Date();
        try {
            List<Long> ids = dto.getIds();
            if (ids.isEmpty()) {
                throw new ApiException("请选择检查记录");
            }
            List<Map<String,Object>> objects = new ArrayList<>();
            int j = 1;
            for (Long id : ids) {
                QueryWrapper<Test> wrapper = new QueryWrapper<>();
                wrapper.eq("id",id);
                Test test = baseMapper.selectList(wrapper).get(0);
                System.out.println(test.toString());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("j",j);
                hashMap.put("project",test.getProject());
                hashMap.put("dangerDescription",test.getDangerDescription());
                ArrayList<PictureRenderData> imageList = new ArrayList<>();
                String images = test.getPicture();
                if (StringUtils.isNotBlank(images)) {
                    for (String s : images.split(",")) {
                        URL url = new URL(s);
                        imageList.add(new PictureRenderData(200, 100, PictureType.PNG, url.openStream()));
                    }
                }
                hashMap.put("images", imageList);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                hashMap.put("date", simpleDateFormat.format(test.getUpdateTime()));
                hashMap.put("person",test.getUpdatePerson());
                objects.add(hashMap);
                j++;
            }
           objects.stream().forEach(s -> {
                System.out.println(s);
            });
            HashMap<String, Object> map = new HashMap<>();
            map.put("record",objects);
            map.put("record1","1");
            String targetPath = "D:\\Java\\big3\\driving_school_registration\\service\\src\\main\\resources\\outTemplate.docx";
            XWPFTemplate template = XWPFTemplate.compile("D:\\Java\\SpringBootStudy\\SpringBootDemo01\\src\\test\\resources\\template\\template.docx").render(map);
            template.writeAndClose(new FileOutputStream(targetPath));
            File input = new File("D:\\Java\\big3\\driving_school_registration\\service\\src\\main\\resources\\outTemplate.docx");
            File output = new File("D:\\Java\\big3\\driving_school_registration\\service\\src\\main\\resources\\testPdf.pdf");
            InputStream docxInputStream = new FileInputStream(input);
            OutputStream outputStream = Files.newOutputStream(output.toPath());
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();

            String exportName = new SimpleDateFormat("yyyyMMddHHmm").format(date);
            response.setCharacterEncoding("UTF-8");
            response.reset();
            response.setHeader("Access-Control-Expose-Headers","Content-Disposition");//暴露Content-Disposition
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(exportName+".pdf", "UTF-8"));
            //            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ServletOutputStream out = response.getOutputStream();
            InputStream in = new FileInputStream("D:\\Java\\big3\\driving_school_registration\\service\\src\\main\\resources\\testPdf.pdf");

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.write(buffer);
            out.flush();
            out.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
