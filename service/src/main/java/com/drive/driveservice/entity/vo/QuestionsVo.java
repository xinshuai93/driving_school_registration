package com.drive.driveservice.entity.vo;

import com.drive.driveservice.entity.Options;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
public class QuestionsVo {
    private String id;
    private String content;
    private String[] choose;
    private List<Options> optionsList;
    private String key;  //正确答案
    private Integer type;
    private String explains;
    //是否选对:1:正确,2:错误
    private Integer isTrue;
}
