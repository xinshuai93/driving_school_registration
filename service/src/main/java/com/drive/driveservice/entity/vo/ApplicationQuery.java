package com.drive.driveservice.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ApplicationQuery {
    Date begin;
    Date end;
    Boolean isPass;
}
