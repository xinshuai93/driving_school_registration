package com.drive.driveservice.service;

import com.drive.driveservice.dto.RecordReportDTO;
import com.drive.driveservice.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2023-06-01
 */
public interface TestService extends IService<Test> {

    void reportCheckRecord(RecordReportDTO dto, HttpServletResponse response);
}
