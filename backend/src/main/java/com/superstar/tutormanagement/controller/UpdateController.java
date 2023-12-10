package com.superstar.tutormanagement.controller;

import com.superstar.tutormanagement.controller.request.*;
import com.superstar.tutormanagement.controller.response.CompleteResponse;
import com.superstar.tutormanagement.controller.response.PullProfileResponse;
import com.superstar.tutormanagement.handler.TokenLimit;
import com.superstar.tutormanagement.service.UpdateService;
import com.superstar.tutormanagement.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController {
    @Autowired
    private UpdateService updateService;


    @PostMapping(value = "/profile")
    @TokenLimit
    public CommonResult<PullProfileResponse> updateProfile(@RequestBody UpdateProfileRequest request) {
        return CommonResult.success(updateService.updateProfile(request));
    }

    @PostMapping(value = "/student/course")
    @TokenLimit
    public CommonResult<Integer> studentChooseCourse(@RequestBody ChooseCourseRequest request) {
        return CommonResult.success(updateService.studentChooseCourse(request));
    }

    @PostMapping(value = "/tutor/course")
    @TokenLimit
    public CommonResult<Integer> tutorChooseCourse(@RequestBody ChooseCourseRequest request) {
        return CommonResult.success(updateService.tutorChooseCourse(request));
    }

    @PostMapping(value = "/book/student")
    @TokenLimit
    public CommonResult<Integer> studentBookTutor(@RequestBody StudentBookRequest request) {
        return CommonResult.success(updateService.studentBookTutor(request));
    }

    @PostMapping(value = "/book/tutor")
    @TokenLimit
    public CommonResult<Integer> tutorConfirmBooking(@RequestBody TutorConfirmBookingRequest request) {
        return CommonResult.success(updateService.tutorConfirmBooking(request));
    }

    @PostMapping(value = "/book/complete")
    @TokenLimit
    public CommonResult<CompleteResponse> studentCompleteBooking(@RequestBody StudentCompleteBookingRequest request) {
        return CommonResult.success(updateService.studentCompleteBooking(request));
    }

    @PostMapping(value = "/score")
    @TokenLimit
    public CommonResult<Integer> score(@RequestBody ScoreRequest request) {
        return CommonResult.success(updateService.score(request));
    }

    @PostMapping(value = "/newMsg")
    @TokenLimit
    public CommonResult<Integer> sendMsg(@RequestBody SendNewMsgRequest request) {
        return CommonResult.success(updateService.sendMsg(request));
    }
}
