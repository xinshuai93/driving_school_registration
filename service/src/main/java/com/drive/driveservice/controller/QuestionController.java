package com.drive.driveservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drive.commonutils.R;
import com.drive.driveservice.dto.AddAnswerDTO;
import com.drive.driveservice.entity.Options;
import com.drive.driveservice.entity.Question;
import com.drive.driveservice.entity.vo.QuestionQuery;
import com.drive.driveservice.entity.vo.QuestionsVo;
import com.drive.driveservice.service.OptionService;
import com.drive.driveservice.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
@CrossOrigin
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


    @ApiOperation("查找单个选择题目")
    @GetMapping("getQuestion/{id}")
    public R getQuestion(@PathVariable String id){
       Question byId = questionService.getById(id);
       if (byId.getType()==3){
           Options options = optionService.getById(byId.getAnswerId());
           String answer = options.getContent();
           return R.ok().data("question",byId).data("answer",answer);

       }
       else{
           QueryWrapper<Options> wrapper = new QueryWrapper<>();
           wrapper.eq("question_id",id);
           List<Options> list = optionService.list(wrapper);
           Options options = optionService.getById(byId.getAnswerId());
           String answer = options.getContent();
           return R.ok().data("byId",byId).data("list",list).data("answer",answer);
       }
    }

    @ApiOperation("查看单个答案")
    @GetMapping("getAnswer/{id}")
    public R getAnswer(@PathVariable String id){
        Options byId = optionService.getById(id);
        return R.ok().data("byId",byId);
    }

    @ApiOperation("查询所有选项")
    @GetMapping("getAllOption/{id}")
    public R getAllOption(@PathVariable String id){
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("id",id);
        if (questionService.getOne(questionQueryWrapper).getType()==3){
            QueryWrapper<Options> wrapper = new QueryWrapper<>();
            wrapper.le("id",100);
            wrapper.ge("id",99);
            List<Options> list = optionService.list(wrapper);
            return R.ok().data("option",list);
        }else {
            QueryWrapper<Options> wrapper = new QueryWrapper<>();
            List<Options> list = optionService.list(wrapper.eq("question_id",id));
            return R.ok().data("option",list);
        }

    }

    @ApiOperation("修改答案")
    @GetMapping("updateAnswer/{qid}/{aid}")
    public R updateAnswer(@PathVariable String qid,@PathVariable String aid){
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("id",qid);
        Question question = questionService.getOne(wrapper);
        question.setAnswerId(aid);
        questionService.updateById(question);
        return R.ok();
    }

    @ApiOperation("修改题目")
    @PostMapping("updateQuestion")
    public R updateQuestion(@RequestBody Question question){
        questionService.updateById(question);
        return R.ok();
    }

    @ApiOperation("修改选项")
    @PostMapping("updateOption")
    public R updateOption(@RequestBody Options options){
        optionService.updateById(options);
        return R.ok();
    }

    //TODO 考试试卷发放
    //TODO 考试完了对答案，得出分数


    //平时练习
    @ApiOperation("练习或者考试组成题库")
    @GetMapping("generateExercise1")
    public R generateExercise1(){
        //单选择题
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("type",1);
        List<Question> lista = questionService.list(wrapper);
        List<String> idList = new ArrayList<>();
        for (Question question : lista) {
            idList.add(question.getId());
        }
        List<String> randoms = createRandoms(idList, 2);
        QueryWrapper<Question> wrapper2 = new QueryWrapper<>();
        wrapper2.in("id",randoms);
        List<Question> list = questionService.list(wrapper2);
        List<QuestionsVo> objects1 = new ArrayList<>();
        for (Question question : list) {
            QuestionsVo vo = new QuestionsVo();
            String id = question.getId();
            vo.setId(id);
            vo.setContent(question.getContent());
            //获取选项
            List<Options> options = getOptions(id);
            vo.setOptionsList(options);
            vo.setType(question.getType());
            vo.setKey(question.getAnswerId());
            vo.setExplains(question.getExplains());
            objects1.add(vo);
        }

        //多选
        QueryWrapper<Question> wrappera = new QueryWrapper<>();
        wrappera.eq("type",2);
        List<Question> listaa = questionService.list(wrappera);
        List<String> idLista = new ArrayList<>();
        for (Question question : listaa) {
            idLista.add(question.getId());
        }
        List<String> randomsa = createRandoms(idLista, 2);
        QueryWrapper<Question> wrapper2a = new QueryWrapper<>();
        wrapper2a.in("id",randomsa);
        List<Question> listz = questionService.list(wrapper2a);
        for (Question question : listz) {
            QuestionsVo vo = new QuestionsVo();
            String id = question.getId();
            vo.setId(id);
            vo.setContent(question.getContent());
            //获取选项
            List<Options> options = getOptions(id);
            vo.setOptionsList(options);
            vo.setType(question.getType());
            vo.setKey(question.getAnswerId());
            vo.setExplains(question.getExplains());
            objects1.add(vo);
        }

        //判断
        QueryWrapper<Question> wrapperb = new QueryWrapper<>();
        wrapperb.eq("type",3);
        List<Question> listaab = questionService.list(wrapperb);
        List<String> idListab = new ArrayList<>();
        for (Question question : listaab) {
            idListab.add(question.getId());
        }
        List<String> randomsab = createRandoms(idListab, 2);
        QueryWrapper<Question> wrapper2ab = new QueryWrapper<>();
        wrapper2ab.in("id",randomsab);
        List<Question> listzb = questionService.list(wrapper2ab);
        for (Question question : listzb) {
            QuestionsVo vo = new QuestionsVo();
            String id = question.getId();
            vo.setId(id);
            vo.setContent(question.getContent());
            //获取选项
            List<Options> options = getOptions("0");
            vo.setOptionsList(options);
            vo.setType(question.getType());
            vo.setKey(question.getAnswerId());
            vo.setExplains(question.getExplains());
            objects1.add(vo);
        }
        return R.ok().data("objects1",objects1);
    }

    //如果是考试的话，就根据每道题的类型和是否选正确来计算得分
    @ApiOperation("平时练题试卷得出答案和解析")
    @PostMapping("getScore")
    public R getScore(@RequestBody List<QuestionsVo> list) {
        List<QuestionsVo> objects = new ArrayList<>();
        for (QuestionsVo questionsVo : list) {
            String id = questionsVo.getId();
            Integer type = questionsVo.getType();
            String[] choose = questionsVo.getChoose();
            Arrays.sort(choose);
            //获取数据库的题
            Question byId = questionService.getById(id);
            String[] split = byId.getAnswerId().split(",");
            Arrays.sort(split);
            QuestionsVo vo = new QuestionsVo();
            vo.setExplains(byId.getExplains());
            vo.setId(id);
            vo.setContent(byId.getContent());
            vo.setChoose(choose);
            vo.setOptionsList(questionsVo.getOptionsList());
            vo.setType(type);
            if (type == 1) {
                String content = optionService.getById(byId.getAnswerId()).getContent();
                vo.setKey(content);
                if (choose[0].equals(split[0])) {
                    vo.setIsTrue(1);  //如果正确那么就设置为1
                }else {
                    vo.setIsTrue(2);  //否则为2
                }
            } else if (type == 2) {
                StringBuilder Keys = new StringBuilder();
                for (String s : split) {
                    String content = optionService.getById(s).getContent();
                    Keys.append(content).append(";");
                }
                String Keys2 = Keys.toString();
                vo.setKey(Keys2);
                int flag = 0;
                for (int i = 0; i < split.length; i++) {
                    if (!choose[i].equals(split[i])){
                        flag++;
                        break;
                    }
                }
                if (flag != 0) {
                    vo.setIsTrue(2);
                }else {
                    vo.setIsTrue(1);
                }
            } else if (type == 3) {
                String content = optionService.getById(byId.getAnswerId()).getContent();
                vo.setKey(content);
                if (choose[0].equals(split[0])) {
                    vo.setIsTrue(1);  //如果正确那么就设置为1
                }else {
                    vo.setIsTrue(2);  //否则为2
                }
            }
            objects.add(vo);
        }
        return R.ok().data("data",objects);
    }

    //随机在数组里选出几个数
    private static List<String> createRandoms(List<String> list, int n) {
        Map<Integer,String> map = new HashMap();
        List<String> news = new ArrayList();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int)(Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    news.add(list.get(random));
                }
            }
            return news;
        }
    }

    //根据题目id获取选项
    public List<Options> getOptions(String id) {
        QueryWrapper<Options> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id",id);
        return optionService.list(wrapper);
    }


}

