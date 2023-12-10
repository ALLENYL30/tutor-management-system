package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 老师信息实体
 */
public class TutorDO{
    private String account;
    private String userName;
    private String address;
    private String phone;
    private String timeZone;
    private String bio;
    private String filePath;
    private InputStream image;
    private int score;
    private int number;
}

