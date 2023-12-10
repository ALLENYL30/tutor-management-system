package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 课程信息实体
 */
public class CourseDO {
    private Integer id;
    private String name;
    private String courseType;
    private String description;
    private String createTime;
    private String updateTime;
    private String isDelete;
    private InputStream image;
}
