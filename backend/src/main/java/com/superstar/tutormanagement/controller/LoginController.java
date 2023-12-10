package com.superstar.tutormanagement.controller;

import com.superstar.tutormanagement.controller.request.LoginRequest;
import com.superstar.tutormanagement.controller.response.LoginResponse;
import com.superstar.tutormanagement.service.LoginService;
import com.superstar.tutormanagement.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/student")
    public CommonResult<LoginResponse> studentLogin(@RequestBody LoginRequest request) {
        return CommonResult.success(loginService.studentLogin(request));
    }
    @PostMapping("/tutor")
    public CommonResult<LoginResponse> tutorLogin(@RequestBody LoginRequest request) {
        return CommonResult.success(loginService.tutorLogin(request));
    }
    @PostMapping("/common")
    public CommonResult<LoginResponse> commonLogin(@RequestBody LoginRequest request) {
        return CommonResult.success(loginService.commonLogin(request));
    }

    @PostMapping("/admin")
    public CommonResult<LoginResponse> adminLogin(@RequestBody LoginRequest request) {
        return CommonResult.success(loginService.adminLogin(request));
    }
}
