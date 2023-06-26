package com.drive.driveservice.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.drive.commonutils.R;
import com.drive.commonutils.excptionhandler.GuliException;
import com.drive.driveservice.service.VideoService;
import com.drive.driveservice.utils.ConstantVodUtils;
import com.drive.driveservice.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-06-24
 */
@Api(tags = "阿里云视频")
@RestController
@RequestMapping("/driveservice/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation("上传视频到阿里云，返回视频id")
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        String videoId = videoService.uploadAliyun(file);
        return R.ok().data("videoId",videoId);
    }

    @ApiOperation("根据视频id删除阿里云视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            //向request设置删除的视频id
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @ApiOperation("删除多个阿里云视频:参数多个视频id list")
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        if (videoIdList.size() == 0) {
            return R.error();
        }
        videoService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频凭证
    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("getPalyAuth/{id}")
    public R getPlayAuth(@PathVariable String id) throws Exception{
        //获取阿里云存储相关常量
        String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;

        //初始化
        DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);

        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(id);

        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);

        //得到播放凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }


    




}

