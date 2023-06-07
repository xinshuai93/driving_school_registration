package com.drive.driveservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
@ApiModel(value = "生成报告DTO",description = "生成报告DTO")
public class RecordReportDTO {
    private List<Long> ids;
}
