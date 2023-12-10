package com.superstar.tutormanagement.service.impl;

import com.superstar.tutormanagement.controller.request.DeleteBookRequest;
import com.superstar.tutormanagement.controller.request.DeleteCourseRequest;
import com.superstar.tutormanagement.controller.request.DeleteSelectCourseRequest;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.repository.dao.DeleteDao;
import com.superstar.tutormanagement.repository.entity.UserRoleDO;
import com.superstar.tutormanagement.service.DeleteService;
import com.superstar.tutormanagement.utils.TokenStorage;
import com.superstar.tutormanagement.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeleteServiceImpl implements DeleteService {
    @Autowired
    private TokenStorage tokenStorage;

    @Resource
    private DeleteDao deleteDao;

    @Override
    /**
     * 根据type删除课程
     */
    public Integer deleteCourse(DeleteCourseRequest request) {
        TokenUtils.isAdmin(tokenStorage.getToken());
        int res = deleteCourseByType(request);
        if (res != 1) {
            throw new TutorManagementException(ResultCodeEnum.COURSE_EMPTY);
        }
        return res;
    }
    /**
     * student course can be deleted
     */
    @Override
    public Integer deleteStudentCourse(DeleteSelectCourseRequest request) {
        String courseName = request.getCourseName();
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        Integer studentId = deleteDao.getStudentId(account);
        Integer courseId = deleteDao.getCourseId(courseName);
        return deleteDao.physicalDelete(studentId, courseId);
    }

    /**
     * 取消老师选课
     * @param request
     * @return
     */
    @Override
    public Integer deleteTutorCourse(DeleteSelectCourseRequest request) {
        String courseName = request.getCourseName();
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        int tutorId = deleteDao.getTutorId(account);
        int courseId = deleteDao.getCourseId(courseName);
        return deleteDao.deleteTutorSelectedCourse(tutorId, courseId);
    }

    /**
     * 取消预约
     * @return
     * @param request
     */
    @Override
    public Integer deleteBook(DeleteBookRequest request) {
        return  deleteDao.deleteBook(request.getBookId());
    }

    private int deleteCourseByType(DeleteCourseRequest request) {
        if (request.getType() == 0) {
            // 逻辑删除
            return logicallyDelete(request.getName());
        }
        return physicalDelete(request.getName());
    }

    /**
     * 物理删除
     * @param name
     * @return
     */
    private int physicalDelete(String name) {
        return deleteDao.physicalDelete(name);
    }

    /**
     * 逻辑删除
     * @param name
     * @return
     */
    private int logicallyDelete(String name) {
        return deleteDao.logicallyDelete(name);
    }

}
