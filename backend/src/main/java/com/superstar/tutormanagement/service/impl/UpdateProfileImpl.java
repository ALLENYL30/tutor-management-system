package com.superstar.tutormanagement.service.impl;

import com.alibaba.fastjson.JSON;
import com.superstar.tutormanagement.controller.request.*;
import com.superstar.tutormanagement.controller.response.CompleteResponse;
import com.superstar.tutormanagement.controller.response.PullProfileResponse;
import com.superstar.tutormanagement.enums.ConstantEnum;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.repository.dao.PullDao;
import com.superstar.tutormanagement.repository.dao.UpdateDao;
import com.superstar.tutormanagement.repository.entity.ChatDO;
import com.superstar.tutormanagement.repository.entity.ScoreDO;
import com.superstar.tutormanagement.repository.entity.UserRoleDO;
import com.superstar.tutormanagement.service.PullService;
import com.superstar.tutormanagement.service.UpdateService;
import com.superstar.tutormanagement.utils.ChatUtils;
import com.superstar.tutormanagement.utils.TimeUtils;
import com.superstar.tutormanagement.utils.TokenStorage;
import com.superstar.tutormanagement.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UpdateProfileImpl implements UpdateService {
    @Autowired
    private TokenStorage tokenStorage;

    @Resource
    private UpdateDao updateDao;

    @Resource
    private PullDao pullDao;

    @Autowired
    private PullService pullService;

    @Override
    public PullProfileResponse updateProfile(UpdateProfileRequest request) {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String tableName = ConstantEnum.getEnumFromType(userRoleDO.getRole()).getCode();
        String account = userRoleDO.getAccountNumber();
        /*
        组装set语句
         */
        String set = createSet(userRoleDO.getRole(), request);
        int isUpdate = 0;

        if (request.getImage().isEmpty()) {
            isUpdate = updateDao.updateProfile(tableName, set, account);
        } else {
            // 含图片
            byte[] image = Base64.getDecoder().decode(request.getImage());
            isUpdate = updateDao.updateProfileWithImage(tableName, set, image, account);
        }

        if (isUpdate != 1) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }
        PullProfileResponse response = pullService.pullProfile();
        return response;
    }

    /**
     * 学生选课
     * @param request
     * @return
     */
    @Override
    public Integer studentChooseCourse(ChooseCourseRequest request) {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        /*
        获取到课程id和学生id
         */
        Integer courseId = getCourseId(request.getCourseName());
        Integer studentId = updateDao.getStudentId(account);
        /*
        选课
         */
        int chooseCourse;
        try{
            chooseCourse = updateDao.studentChooseCourse(courseId, studentId);
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.CHOOSE_COURSE_FAILED);
        }
        return chooseCourse;
    }

    /**
     * 老师认领课
     * @param request
     * @return
     */
    @Override
    public Integer tutorChooseCourse(ChooseCourseRequest request) {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        /*
        获取老师id和课程id
         */
        Integer courseId = getCourseId(request.getCourseName());
        Integer tutorId = updateDao.getTutorId(account);
        /*
        选课
         */
        int chooseCourse;
        try {
            chooseCourse = updateDao.tutorChooseCourse(courseId, tutorId);
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.CHOOSE_COURSE_FAILED);
        }
        return chooseCourse;
    }

    /**
     * 学生预约老师时间
     * @param request
     * @return
     */
    @Override
    public Integer studentBookTutor(StudentBookRequest request) {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        int res = 0;
        try {
            Integer tutorId = updateDao.getTutorIdByName(request.getTutorName());
            Integer studentId = updateDao.getStudentId(account);
            Timestamp timestamp = TimeUtils.getTime(request.getTime());
            res = updateDao.studentBook(tutorId, studentId, timestamp);
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        return res;
    }

    /**
     * 老师确认预约
     * @param request
     * @return
     */
    @Override
    public Integer tutorConfirmBooking(TutorConfirmBookingRequest request) {
        int bookingId = request.getBookId();
        int res = 0;
        try {
            res = updateDao.confirmBookingById(bookingId);
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        return res;
    }

    /**
     * 学生确认完成订单
     * @param request
     * @return
     */
    @Override
    public CompleteResponse studentCompleteBooking(StudentCompleteBookingRequest request) {
        int bookingId = request.getBookId();
        int res = 0;
        try {
            res = updateDao.studentCompleteBooking(bookingId);
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        if (res != 1) {
            throw new TutorManagementException(ResultCodeEnum.BOOKING_COMPLETE_ERROE);
        }

        return new CompleteResponse(bookingId);
    }

    /**
     * 学生给老师打分
     * @param request
     * @return
     */
    @Override
    public Integer score(ScoreRequest request) {
        int res;
        int bookId = request.getBookId();
        int score = request.getScore();
        try {
            Integer tutorId = updateDao.getTutorIdByBooking(bookId);
            ScoreDO scoreDO = updateDao.getScore(tutorId);
            score += scoreDO.getScore();
            int number = scoreDO.getNumber() + 1;
            res = updateDao.setScore(tutorId, score, number);
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        return res;
    }

    /**
     * 发送消息
     * @param request
     * @return
     */
    @Override
    public Integer sendMsg(SendNewMsgRequest request) {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        String sender;
        if (userRoleDO.getRole().equals("student")) {
            sender = pullDao.getStudentName(account);
        } else {
            sender = pullDao.getTutorName(account);
        }
        String receiver = request.getReceiver();
        String message = request.getMessage();
        /*
        检查有没有这两个人
         */
        int isExist1 = pullDao.isChatExist(sender, receiver);
        int isExist2 = pullDao.isChatExist(receiver, sender);
        if (isExist1 + isExist2 != 2) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        /*
        获取到(sender:sender, receiver:receiver)，往里加新的消息
         */
        String historyMsg = pullDao.getHistoryMsg(sender, receiver); // 获取到我发送给对方的消息
        List<ChatDO> historyMsgList = JSON.parseArray(historyMsg, ChatDO.class);
        ChatDO chatDO = ChatUtils.createChatDO(sender, message); // 创建新的消息记录
        historyMsgList.add(chatDO);
        List<ChatDO> newMsgList = historyMsgList; // 新的消息记录列表
        String newMsg = ChatUtils.List2JsonStr(newMsgList);

        /*
        把新组装的消息放进(sender:sender, receiver:receiver)中，更新update_time
         */
        int updateFlag1 = updateDao.updateMsg(sender, receiver, newMsg); // 更新 此时update_time > load_time，轮询接口会反馈
        return updateFlag1;
    }

    /**
     * 组装set语句
     * @param role
     * @param request
     * @return
     */
    public String createSet(String role, UpdateProfileRequest request) {
        if (role.equals("student") && (!request.getBio().isEmpty() || !request.getFilePath().isEmpty())) {
            throw new TutorManagementException(ResultCodeEnum.ILLEGAL_PARAM);
        }
        return AssemblingSetStatements(request);
    }


    /**
     * set语句
     * 逗号在这里面添加，末尾会有逗号，UpdateDao里面的update_time字段之前就不需要加逗号了
     * @param request
     * @return
     */
    private String AssemblingSetStatements(UpdateProfileRequest request) {
        StringBuilder builder = new StringBuilder();

        Class<?> clazz = request.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                if (ConstantEnum.containsKey(fieldName)) {
                    fieldName = ConstantEnum.getEnumFromType(fieldName).getCode();
                }
                String fieldValue = (String) field.get(request);

                if (fieldValue != null && !fieldValue.isEmpty()) {
                    if (!fieldName.equals("image")) {
                        builder.append(fieldName).append(" = '").append(fieldValue).append("', ");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    /**
     * 获取课程id
     * @param name
     * @return
     */
    private Integer getCourseId(String name){
        Integer courseId = updateDao.getCourseId(name);
        if (null == courseId) {
            throw new TutorManagementException(ResultCodeEnum.COURSE_EMPTY);
        }
        return courseId;
    }
}
