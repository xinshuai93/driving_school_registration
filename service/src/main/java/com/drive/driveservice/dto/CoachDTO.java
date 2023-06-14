package com.drive.driveservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CoachDTO {
    private String coachId;
    private String name;
    private String phone;
    private Date begin;
    private Date end;
}
