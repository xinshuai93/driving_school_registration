package com.drive.driveservice.service.impl;

import com.drive.driveservice.entity.Application;
import com.drive.driveservice.mapper.ApplicationMapper;
import com.drive.driveservice.service.ApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生报名表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

}
