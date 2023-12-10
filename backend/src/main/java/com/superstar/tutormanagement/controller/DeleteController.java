package com.superstar.tutormanagement.controller;


import com.superstar.tutormanagement.controller.request.DeleteBookRequest;
import com.superstar.tutormanagement.controller.request.DeleteCourseRequest;
import com.superstar.tutormanagement.controller.request.DeleteSelectCourseRequest;
import com.superstar.tutormanagement.handler.TokenLimit;
import com.superstar.tutormanagement.service.DeleteService;
import com.superstar.tutormanagement.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/delete")
public class DeleteController {
    @Autowired
    private DeleteService deleteService;

    @PostMapping(value = "/course")
    @TokenLimit
    public CommonResult<Integer> deleteCourse(@RequestBody DeleteCourseRequest request) {
        return CommonResult.success(deleteService.deleteCourse(request));
    }
    @PostMapping(value = "/student/course")
    @TokenLimit
    public CommonResult<Integer> deleteStudentCourse(@RequestBody DeleteSelectCourseRequest request) {
        return CommonResult.success(deleteService.deleteStudentCourse(request));
    }

    @PostMapping(value = "/tutor/course")
    @TokenLimit
    public CommonResult<Integer> deleteTutorCourse(@RequestBody DeleteSelectCourseRequest request) {
        return CommonResult.success(deleteService.deleteTutorCourse(request));
    }

    @PostMapping(value = "/book")
    @TokenLimit
    public CommonResult<Integer> deleteBook(@RequestBody DeleteBookRequest request) {
        return CommonResult.success(deleteService.deleteBook(request));
    }

}
