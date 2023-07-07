package com.drive.driveservice.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ExamInfoVo {
    private String subjectType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;
    private Integer chooseNum;
    private Integer multipleNum;
    private Integer judgeNum;
    private Double duration;
}
