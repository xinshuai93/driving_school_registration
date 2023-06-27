package com.drive.driveservice.entity.vo;

import com.drive.driveservice.entity.Options;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
//题目内容和选项对象
@Data
public class QuestionAndOptionVo {
    private String question;
    private List<Options> optionList;
}
