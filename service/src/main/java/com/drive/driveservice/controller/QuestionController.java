package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.dto.AddAnswerDTO;
import com.drive.driveservice.entity.Options;
import com.drive.driveservice.entity.Question;
import com.drive.driveservice.entity.vo.QuestionAndOptionVo;
import com.drive.driveservice.entity.vo.QuestionQuery;
import com.drive.driveservice.service.OptionService;
import com.drive.driveservice.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题目-答案表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-27
 */
@Api(tags = "题目管理")
@RestController
@RequestMapping("/driveservice/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionService optionService;

    @ApiOperation("添加题目")
    @PostMapping("addQuestion")
    public R addQuestion(@RequestBody Question question) {
        boolean save = questionService.save(question);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("添加答案时查询选项")
    @GetMapping("getOption/{id}")
    public R getOption(@PathVariable String id) {
        //先判断是选择题还是判断题
        Integer type = questionService.getById(id).getType();
        List<Options> objects = new ArrayList<>(); //选择题的选项
        //判断题的选项
        List<Integer> objects1 = new ArrayList<>();
        //如果是判断题
        if (type == 3) {
            objects1.add(99);
            objects1.add(100);
            return R.ok().data("options",objects1);
        } else {
            QueryWrapper<Options> wrapper = new QueryWrapper<>();
            wrapper.eq("question_id",id);
            List<Options> list = optionService.list(wrapper);
            return R.ok().data("options",list);
        }
    }

    @ApiOperation("添加答案(前端将答案拼接成,相隔的)")
    @PostMapping("addSimpleAnswer")
    public R addSimpleAnswer(@RequestBody AddAnswerDTO dto){
        String s = questionService.addSimpleAnswer(dto);
        return R.ok().data("message",s);
    }

    @ApiOperation("分页查询所有的题目")
    @PostMapping("pageList")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit,
                      @RequestBody(required = false) QuestionQuery questionQuery){
        Page<Question> page1 = new Page<>(page, limit);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(questionQuery.getContent()),"content",questionQuery.getContent());
        wrapper.eq(questionQuery.getType() != null,"type",questionQuery.getType());
        wrapper.eq(questionQuery.getKemu() != null,"kemu",questionQuery.getKemu());
        IPage<Question> page2 = questionService.page(page1, wrapper);
        List<Question> records = page2.getRecords();
        return R.ok().data("records",records);
    }

    //TODO 修改题目和选项答案


    //平时练习
    @ApiOperation("平时练习组成题库")
    @GetMapping("generateExercise")
    public R generateExercise(){
        //单选题
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("type",1);
        List<Question> list = questionService.list(wrapper);
        List<Map<String, QuestionAndOptionVo>> objects = new ArrayList<>();
        for (Question question : list) {  //问题
            HashMap<String, QuestionAndOptionVo> hashMap = new HashMap<>();
            String id = question.getId();
            String content = question.getContent();
            QueryWrapper<Options> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("question_id",id);
            List<Options> list1 = optionService.list(wrapper1);
            //选项
            List<Options> optionList = new ArrayList<>(list1);
            QuestionAndOptionVo optionVo = new QuestionAndOptionVo();
            optionVo.setQuestion(content);
            optionVo.setOptionList(optionList);
            hashMap.put(id,optionVo);
            objects.add(hashMap);
        }
        return R.ok().data("objects",objects);
    }


}

