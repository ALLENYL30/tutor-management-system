package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 对接数据库的课程信息实体
 */
public class PullCourseInfoDO {
    private String name;
    private String description;
    private String courseType;
    private InputStream image;
}
