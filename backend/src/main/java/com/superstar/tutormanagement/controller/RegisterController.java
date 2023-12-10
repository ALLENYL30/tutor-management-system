package com.superstar.tutormanagement.controller;

import com.superstar.tutormanagement.controller.request.StudentRegisterRequest;
import com.superstar.tutormanagement.controller.request.TutorRegisterRequest;
import com.superstar.tutormanagement.service.RegisterService;
import com.superstar.tutormanagement.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/student")
    public CommonResult<Integer> studentRegister(@RequestBody StudentRegisterRequest request) {
        return CommonResult.success(registerService.studentRegister(request));
    }

    @PostMapping("/tutor")
    public CommonResult<Integer> tutorRegister(@RequestBody TutorRegisterRequest request) {
        return CommonResult.success(registerService.tutorRegister(request));
    }
}
