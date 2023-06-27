package com.drive.driveservice.service.impl;

import com.drive.commonutils.R;
import com.drive.driveservice.dto.AddAnswerDTO;
import com.drive.driveservice.entity.Question;
import com.drive.driveservice.mapper.QuestionMapper;
import com.drive.driveservice.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目-答案表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-06-27
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionService questionService;

    //添加答案
    @Override
    public String addSimpleAnswer(AddAnswerDTO dto) {
        String id = dto.getId();
        String answerId = dto.getAnswerId();
        Question byId = questionService.getById(id);
        byId.setAnswerId(answerId);
        boolean b = questionService.updateById(byId);
        if (b) {
            return "success";
        }
        return "fail";
    }
}
