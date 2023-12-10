package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 学生已选课程给前端的
 */
public class CommonCourseResponseDO {
    private String name;
    private String description;
    private String courseType;
    private String image;
}
