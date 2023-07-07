package com.drive.driveservice.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ExamRecordVo {
    private String subject;
    private String state;
    private Integer score;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

}
