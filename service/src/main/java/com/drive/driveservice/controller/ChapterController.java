package com.drive.driveservice.controller;


import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.drive.commonutils.R;
import com.drive.commonutils.vo.OptionVo;
import com.drive.driveservice.dto.ChapterDTO;
import com.drive.driveservice.entity.Chapter;
import com.drive.driveservice.entity.vo.ChapterAndVideoVo;
import com.drive.driveservice.service.ChapterService;
import com.drive.driveservice.utils.ConstantVodUtils;
import com.drive.driveservice.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @ApiOperation("添加章节")
    @PostMapping("create")
    public R create(@RequestBody ChapterDTO dto) {
        String[] videoArr = dto.getVideoArr();
        String videoIds = "";
        for (String s : videoArr) {
            videoIds += s;
        }
        Chapter chapter = new Chapter();
        chapter.setVideoId(videoIds);
        chapter.setSubject(dto.getSubject());
        chapter.setChapterName(dto.getChapterName());
        boolean save = chapterService.save(chapter);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("删除章节和对应的视频")
    @DeleteMapping("delete/{id}")
    public R deleteById(@PathVariable String id) {
        chapterService.delete(id);
        return R.ok();
    }

    @ApiOperation("查看所有章节和视频")
    @GetMapping("getChapterAndVideo")
    public R getChapterAndVideo() throws ClientException {
        List<Chapter> list = chapterService.list(null);
        List<ChapterAndVideoVo> objects = new ArrayList<>();
        for (Chapter chapter : list) {
            String[] split = chapter.getVideoId().split(",");
            ChapterAndVideoVo videoVo = new ChapterAndVideoVo();
            videoVo.setId(chapter.getId());
            videoVo.setChapterName(chapter.getChapterName());
            List<OptionVo> list1 = new ArrayList<>();
            for (String s : split) {
                String playAuth = getPlayAuth1(s);
                OptionVo optionVo = new OptionVo();
                optionVo.setKey(s);
                optionVo.setValue(playAuth);
                list1.add(optionVo);
            }
            videoVo.setVideoPlayAuth(list1);
            objects.add(videoVo);
        }
        return R.ok().data("objects",objects);
    }

    //根据视频id获取视频凭证
    public static String getPlayAuth1(String id) throws ClientException {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;

        //初始化
        DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);

        GetPlayInfoRequest getPlayInfoRequest = new GetPlayInfoRequest();
        getPlayInfoRequest.setVideoId(id);
        GetPlayInfoResponse acsResponse = client.getAcsResponse(getPlayInfoRequest);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = acsResponse.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        return playInfoList.get(0).getPlayURL();
    }



}

