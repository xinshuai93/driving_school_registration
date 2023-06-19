package com.drive.driveservice.service;

import com.drive.commonutils.R;
import com.drive.driveservice.dto.ApplicationDTO;
import com.drive.driveservice.entity.Application;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import java.security.GeneralSecurityException;

/**
 * <p>
 * 学生报名表 服务类
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
public interface ApplicationService extends IService<Application> {

    String sendMail(ApplicationDTO dto) throws GeneralSecurityException, MessagingException;
}
