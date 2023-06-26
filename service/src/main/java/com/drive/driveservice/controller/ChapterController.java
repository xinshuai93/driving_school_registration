package com.drive.driveservice.controller;


import com.drive.commonutils.R;
import com.drive.driveservice.entity.Chapter;
import com.drive.driveservice.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 章节-视频表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-24
 */
@Api(tags = "章节-视频表")
@RestController
@RequestMapping("/driveservice/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @ApiOperation("添加章节")
    @PostMapping("create")
    public R create(@RequestBody Chapter chapter) {
        boolean save = chapterService.save(chapter);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("delete/{id}")
    public R deleteById(@PathVariable String id) {
//        chapterService.delete(id);
        return R.ok();
    }

}

