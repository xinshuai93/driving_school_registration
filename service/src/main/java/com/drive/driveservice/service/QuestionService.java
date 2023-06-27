package com.drive.driveservice.service;

import com.drive.commonutils.R;
import com.drive.driveservice.dto.AddAnswerDTO;
import com.drive.driveservice.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 题目-答案表 服务类
 * </p>
 *
 * @author lx
 * @since 2023-06-27
 */
public interface QuestionService extends IService<Question> {

    String addSimpleAnswer(AddAnswerDTO dto);
}
