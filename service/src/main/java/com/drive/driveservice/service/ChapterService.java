package com.drive.driveservice.service;

import com.drive.driveservice.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 章节-视频表 服务类
 * </p>
 *
 * @author lx
 * @since 2023-06-24
 */
public interface ChapterService extends IService<Chapter> {

    void delete(String id);
}
