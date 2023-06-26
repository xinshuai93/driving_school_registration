package com.drive.driveservice.service.impl;

import com.drive.driveservice.entity.Chapter;
import com.drive.driveservice.mapper.ChapterMapper;
import com.drive.driveservice.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drive.driveservice.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 章节-视频表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-06-24
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    @Override
    public void delete(String id) {
        String[] split = chapterService.getById(id).getVideoId().split(",");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());
        videoService.removeMoreAlyVideo(collect);
    }
}
