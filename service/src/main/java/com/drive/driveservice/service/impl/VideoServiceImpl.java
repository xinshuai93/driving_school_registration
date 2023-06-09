package com.drive.driveservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.drive.commonutils.excptionhandler.GuliException;
import com.drive.driveservice.utils.ConstantVodUtils;
import com.drive.driveservice.entity.Video;
import com.drive.driveservice.mapper.VideoMapper;
import com.drive.driveservice.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drive.driveservice.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-06-24
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public String uploadAliyun(MultipartFile file) {
        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            //向request设置删除的视频id
            String ids = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(ids);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
