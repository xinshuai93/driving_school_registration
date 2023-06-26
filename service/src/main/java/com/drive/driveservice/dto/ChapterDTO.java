package com.drive.driveservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
@ApiModel(value = "添加章节DTO",description = "添加章节DTO")
public class ChapterDTO {
    @ApiModelProperty(value = "章节名")
    private String chapterName;

    @ApiModelProperty(value = "视频id")
    private String videoId;

    @ApiModelProperty(value = "所属科目")
    private String subject;

    @ApiModelProperty(value = "上传的视频id数组")
    private String[] videoArr;
}
