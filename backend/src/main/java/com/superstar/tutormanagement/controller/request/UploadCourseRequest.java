package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 上传课程信息
 */
public class UploadCourseRequest {
    private String name;
    private String description;
    private String courseType; // 课程所属类别，用逗号分割
    private String image;
}
