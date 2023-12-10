package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 给前端的课程信息带token
 */
public class PullCourseInfoWithTokenResponseDO {
    private String name;
    private String description;
    private String courseType;
    private String image;
    private boolean isChoose;
}
