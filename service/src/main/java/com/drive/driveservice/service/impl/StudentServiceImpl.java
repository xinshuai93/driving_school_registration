package com.drive.driveservice.service.impl;

import com.drive.driveservice.entity.Student;
import com.drive.driveservice.mapper.StudentMapper;
import com.drive.driveservice.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
