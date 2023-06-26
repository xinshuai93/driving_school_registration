package com.drive.driveservice.entity.vo;

import com.drive.commonutils.vo.OptionVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
public class ChapterAndVideoVo {
    @ApiModelProperty("章节id")
    private String id;

    @ApiModelProperty("章节名称")
    private String chapterName;

    @ApiModelProperty("视频id和视频凭证")
    private List<OptionVo> videoPlayAuth;
}
