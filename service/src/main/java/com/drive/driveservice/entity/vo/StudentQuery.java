package com.drive.driveservice.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class StudentQuery {
    String name;
    String phone;
    Date begin;
    Date end;
}