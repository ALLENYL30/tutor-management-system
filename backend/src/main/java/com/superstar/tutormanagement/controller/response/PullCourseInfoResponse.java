package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.PullCourseInfoResponseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PullCourseInfoResponse {
    List<PullCourseInfoResponseDO> list;
}
