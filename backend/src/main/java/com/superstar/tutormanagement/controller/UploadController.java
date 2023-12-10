package com.superstar.tutormanagement.controller;

import com.superstar.tutormanagement.controller.request.UploadCourseRequest;
import com.superstar.tutormanagement.controller.response.UploadImageResponse;
import com.superstar.tutormanagement.handler.TokenLimit;
import com.superstar.tutormanagement.service.UploadService;
import com.superstar.tutormanagement.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<UploadImageResponse> uploadImage(@RequestPart("image") MultipartFile file) {
        return CommonResult.success(uploadService.uploadImage(file));
    }

    @PostMapping(value = "/course")
    @TokenLimit
    public CommonResult<Integer> uploadImage(@RequestBody UploadCourseRequest request) {
        return CommonResult.success(uploadService.uploadCourse(request));
    }
}
