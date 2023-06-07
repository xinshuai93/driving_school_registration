package com.drive.driveservice.service.impl;

import com.drive.driveservice.entity.User;
import com.drive.driveservice.mapper.UserMapper;
import com.drive.driveservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-05-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
