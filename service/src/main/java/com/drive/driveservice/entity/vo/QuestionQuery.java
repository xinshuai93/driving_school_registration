package com.drive.driveservice.entity.vo;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@Data
public class QuestionQuery {
    private String content;
    private Integer type;
    private Integer kemu;
}
