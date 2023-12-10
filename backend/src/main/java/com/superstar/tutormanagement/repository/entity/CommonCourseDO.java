package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 学生选的课信息，对应数据库
 */
public class CommonCourseDO {
    private String name;
    private String description;
    private String courseType;
    private InputStream image;
}
