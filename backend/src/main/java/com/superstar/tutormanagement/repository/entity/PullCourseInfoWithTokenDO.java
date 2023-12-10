package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 带token获取课程信息，数据库实体
 */
public class PullCourseInfoWithTokenDO {
    private String name;
    private String description;
    private String courseType;
    private InputStream image;
    private boolean isChoose;
}
