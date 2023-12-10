package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 返回给前端的courses
 */
public class CourseResponseDO {
    private Integer id;
    private String name;
    private String courseType;
    private String description;
    private String createTime;
    private String updateTime;
    private String isDelete;
    private String image;
}
