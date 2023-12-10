package com.superstar.tutormanagement.controller;

import com.superstar.tutormanagement.controller.request.PullChatRequest;
import com.superstar.tutormanagement.controller.request.PullConfirmedRequest;
import com.superstar.tutormanagement.controller.request.PullTutorByNameRequest;
import com.superstar.tutormanagement.controller.response.*;
import com.superstar.tutormanagement.handler.TokenLimit;
import com.superstar.tutormanagement.service.PullService;
import com.superstar.tutormanagement.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pull")
public class PullController {
    @Autowired
    private PullService pullService;

    @PostMapping("/image")
    @TokenLimit
    public CommonResult<PullImageResponse> pullImageUrl() {
        return CommonResult.success(pullService.pullImage());
    }

    @PostMapping("/account")
    @TokenLimit
    public CommonResult<PullAccountResponse> pullAccount() {
        return CommonResult.success(pullService.pullTutorAccount());
    }

    @PostMapping("/profile")
    @TokenLimit
    public CommonResult<PullProfileResponse> pullProfile() {
        return CommonResult.success(pullService.pullProfile());
    }

    @PostMapping("/courses")
    @TokenLimit
    public CommonResult<PullCoursesResponse> pullCourses() {
        return CommonResult.success(pullService.pullCourses());
    }

    @PostMapping("/courseInfo")
    public CommonResult<PullCourseInfoResponse> pullCourseInfo() {
        return CommonResult.success(pullService.pullCourseInfo());
    }

    @PostMapping("/courses/common")
    @TokenLimit
    public CommonResult<PullCommonCourseResponse> pullCommonCourse() {
        return CommonResult.success(pullService.pullCommonCourse());
    }

    @PostMapping("/tutor/unconfirmed")
    @TokenLimit
    public CommonResult<PullUnconfirmedResponse> pullUnconfirmed() {
        return CommonResult.success(pullService.pullUnconfirmed());
    }

    @PostMapping("/tutor/confirmed")
    @TokenLimit
    public CommonResult<PullConfirmedResponse> pullUnconfirmed(@RequestBody PullConfirmedRequest request) {
        return CommonResult.success(pullService.pullConfirmed(request));
    }

    @PostMapping("/book")
    @TokenLimit
    public CommonResult<PullBookingResponse> pullBookings() {
        return CommonResult.success(pullService.pullBookings());
    }

    @PostMapping("/tutor")
    public CommonResult<PullTutorResponse> pullTutors() {
        return CommonResult.success(pullService.pullTutors());
    }

    @PostMapping("/student")
    @TokenLimit
    public CommonResult<PullStudentResponse> pullStudents() {
        return CommonResult.success(pullService.pullStudents());
    }

    @PostMapping("/tutorName")
    @TokenLimit
    public CommonResult<PullTutorResponse> pullTutorByName(@RequestBody PullTutorByNameRequest request) {
        return CommonResult.success(pullService.pullTutorByName(request));
    }

    @PostMapping("/chatList")
    @TokenLimit
    public CommonResult<PullChatListResponse> pullChatList() {
        return CommonResult.success(pullService.pullChatList());
    }

    @PostMapping("/chat")
    @TokenLimit
    public CommonResult<PullChatInfoResponse> pullChatInfo(@RequestBody PullChatRequest request) {
        return CommonResult.success(pullService.pullChatInfo(request));
    }

    @PostMapping("/newMsg")
    @TokenLimit
    public CommonResult<PullChatInfoResponse> pullNewMsg() {
        return CommonResult.success(pullService.pullNewMsg());
    }

    @PostMapping("/course")
    @TokenLimit
    public CommonResult<PullCourseInfoWithTokenResponse> pullCourseInfoWithToken() {
        return CommonResult.success(pullService.pullCourseInfoWithToken());
    }

}
