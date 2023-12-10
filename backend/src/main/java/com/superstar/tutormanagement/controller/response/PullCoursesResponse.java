package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.CourseResponseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 管理员获取课程信息
 */
public class PullCoursesResponse {
    List<CourseResponseDO> list;
}
