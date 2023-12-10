package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 老师对应选的课
 */
public class TutorCourseNameDO {
    private String account;
    private String courseName;
}
