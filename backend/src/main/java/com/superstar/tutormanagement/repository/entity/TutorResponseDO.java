package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 老师信息给前端
 */
public class TutorResponseDO {
    private String account;
    private String userName;
    private String address;
    private String phone;
    private String timeZone;
    private String bio;
    private String filePath;
    private String image;
    private float score;
    private int number;
    private String courseName;
}
