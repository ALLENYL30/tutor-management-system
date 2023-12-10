package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 给前端的课程信息
 */
public class PullCourseInfoResponseDO {
    private String name;
    private String description;
    private String courseType;
    private String image;
}
