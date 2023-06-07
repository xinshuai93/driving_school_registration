package com.driving.demo;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.io.*;
import java.nio.file.Files;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
public class Test {

    @org.junit.jupiter.api.Test
    void pdf() {
        File inputWord = new File("D:\\Java\\big3\\driving_school_registration\\service\\src\\main\\resources\\outTemplate.docx");
        File outputFile = new File("D:\\Java\\big3\\driving_school_registration\\service\\src\\main\\resources\\testPdf.pdf");
        try  {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = Files.newOutputStream(outputFile.toPath());
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            outputStream.close();
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
