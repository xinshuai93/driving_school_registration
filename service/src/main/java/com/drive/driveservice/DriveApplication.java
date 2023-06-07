package com.drive.driveservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @version 1.0
 * @auther xinshuai9333
 */
@SpringBootApplication
@MapperScan("com.drive.*.mapper")
@ComponentScan(basePackages = {"com.drive"})
public class DriveApplication {
    public static void main(String[] args) {
        SpringApplication.run(DriveApplication.class,args);
    }
}
