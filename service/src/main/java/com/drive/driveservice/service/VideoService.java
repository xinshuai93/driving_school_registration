package com.drive.driveservice.service;

import com.drive.driveservice.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author lx
 * @since 2023-06-24
 */
public interface VideoService extends IService<Video> {

    String uploadAliyun(MultipartFile file);
}
