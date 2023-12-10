package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.PullCourseInfoWithTokenResponseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 带token，获取所有课程
 */
public class PullCourseInfoWithTokenResponse {
    List<PullCourseInfoWithTokenResponseDO> list;
}
