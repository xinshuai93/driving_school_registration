package com.drive.driveservice.controller;


import com.drive.commonutils.R;
import com.drive.driveservice.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-24
 */
@RestController
@RequestMapping("/driveservice/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    //上传视频到阿里云
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        String videoId = videoService.uploadAliyun(file);
        return R.ok().data("videoId",videoId);
    }



}

