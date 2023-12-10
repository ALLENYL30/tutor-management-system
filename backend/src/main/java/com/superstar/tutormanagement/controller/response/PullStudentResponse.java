package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.PullStudentDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取学生的信息
 */
public class PullStudentResponse {
    List<PullStudentDO> list;
}
