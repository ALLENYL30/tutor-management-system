package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.CommonCourseResponseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取学生已选课程的信息
 */
public class PullCommonCourseResponse {
    List<CommonCourseResponseDO> list;
}
