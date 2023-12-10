package com.superstar.tutormanagement.service;

import com.superstar.tutormanagement.controller.request.UploadCourseRequest;
import com.superstar.tutormanagement.controller.response.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 接收文件并返回base64码
     * @param file
     * @return
     */
    UploadImageResponse uploadImage(MultipartFile file);

    /**
     * 上传课程
     * @return
     * @param request
     */
    Integer uploadCourse(UploadCourseRequest request);
}
