package com.superstar.tutormanagement.service.impl;

import com.alibaba.fastjson.JSON;
import com.superstar.tutormanagement.controller.request.PullChatRequest;
import com.superstar.tutormanagement.controller.request.PullConfirmedRequest;
import com.superstar.tutormanagement.controller.request.PullTutorByNameRequest;
import com.superstar.tutormanagement.controller.response.*;
import com.superstar.tutormanagement.enums.ConstantEnum;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.repository.dao.PullDao;
import com.superstar.tutormanagement.repository.dao.UpdateDao;
import com.superstar.tutormanagement.repository.entity.*;
import com.superstar.tutormanagement.service.PullService;
import com.superstar.tutormanagement.utils.ChatUtils;
import com.superstar.tutormanagement.utils.TimeUtils;
import com.superstar.tutormanagement.utils.TokenStorage;
import com.superstar.tutormanagement.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.superstar.tutormanagement.utils.ChatUtils.createDefaultMsg;

@Service
public class PullServiceImpl implements PullService {
    @Resource
    private PullDao pullDao;

    @Resource
    private UpdateDao updateDao;

    @Autowired
    private TokenStorage tokenStorage;

    /**
     * 获取图片url
     * @return
     */
    @Override
    public PullImageResponse pullImage() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String table = ConstantEnum.getEnumFromType(userRoleDO.getRole()).getCode();
        String accountNumber = userRoleDO.getAccountNumber();
        InputStream blob = pullDao.getImage(table, accountNumber);
        String url = convertInputStream2Url(blob);
        return new PullImageResponse(url);
    }

    /**
     * 获取所有的老师账户信息
     * @return
     */
    @Override
    public PullAccountResponse pullTutorAccount() {
        TokenUtils.isAdmin(tokenStorage.getToken());
        List<TutorAccountDO> list = pullDao.getAccount();
        return new PullAccountResponse(list);
    }

    /**
     * 获取所有的信息
     * @return
     */
    @Override
    public PullProfileResponse pullProfile() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        /*
        根据身份，获取用户信息
         */
        PullProfileResponse response;
        if (userRoleDO.getRole().equals("student")) {
            // 学生
            StudentInfoDO infoDO = pullDao.getStudentInfo(userRoleDO.getAccountNumber());
            response = new PullProfileResponse(infoDO.getAccount(), infoDO.getUserName(), infoDO.getAddress(),
                    infoDO.getPhone(), infoDO.getTimeZone(), convertInputStream2Url(infoDO.getImage()));
        } else {
            // 老师
            TutorInfoDO infoDO = pullDao.getTutorInfo(userRoleDO.getAccountNumber());
            response = new PullProfileResponse(infoDO.getAccount(), infoDO.getUserName(), infoDO.getAddress(),
                    infoDO.getPhone(), infoDO.getTimeZone(), infoDO.getBio(), infoDO.getFilePath(),
                    convertInputStream2Url(infoDO.getImage()));
        }
        return response;
    }

    /**
     * admin获取所有课程信息
     * @return
     */
    @Override
    public PullCoursesResponse pullCourses() {
        TokenUtils.isAdmin(tokenStorage.getToken());
        List<CourseDO> list = pullDao.getCourses();
        // 并行处理
        List<CourseResponseDO> res = list.parallelStream()
                .map(courseDO -> {
                    CourseResponseDO courseResponseDO = new CourseResponseDO();
                    courseResponseDO.setId(courseDO.getId());
                    courseResponseDO.setName(courseDO.getName());
                    courseResponseDO.setCourseType(courseDO.getCourseType());
                    courseResponseDO.setDescription(courseDO.getDescription());
                    courseResponseDO.setCreateTime(courseDO.getCreateTime());
                    courseResponseDO.setUpdateTime(courseDO.getUpdateTime());
                    courseResponseDO.setIsDelete(courseDO.getIsDelete());

                    if (null != courseDO.getImage()) {
                        String imageUrl = convertInputStream2Url(courseDO.getImage());
                        courseResponseDO.setImage(imageUrl);
                    }

                    return courseResponseDO;
                })
                .collect(Collectors.toList());
        return new PullCoursesResponse(res);
    }

    /**
     * 不用登录，获取课程信息
     * @return
     */
    @Override
    public PullCourseInfoResponse pullCourseInfo() {
        List<PullCourseInfoDO> list = pullDao.getCourseInfo();
        List<PullCourseInfoResponseDO> res = list.parallelStream()
                .map(a -> {
                    PullCourseInfoResponseDO courseInfoResponseDO = new PullCourseInfoResponseDO();
                    courseInfoResponseDO.setName(a.getName());
                    courseInfoResponseDO.setDescription(a.getDescription());
                    courseInfoResponseDO.setCourseType(a.getCourseType());
                    if (null != a.getImage()) {
                        String imageUrl = convertInputStream2Url(a.getImage());
                        courseInfoResponseDO.setImage(imageUrl);
                    }
                    return courseInfoResponseDO;
                }).collect(Collectors.toList());
        return new PullCourseInfoResponse(res);
    }

    /**
     * 学生获取已选课程信息
     * @return
     */
    @Override
    public PullCommonCourseResponse pullCommonCourse() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        List<CommonCourseDO> list = new ArrayList<>();
        try {
            if (userRoleDO.getRole().equals("student")) {
                list = pullDao.pullStudentCourse(userRoleDO.getAccountNumber());
            } else {
                list = pullDao.pullTutorCourse(userRoleDO.getAccountNumber());
            }
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }

        List<CommonCourseResponseDO> res = list.parallelStream()
                .map(a -> {
                    CommonCourseResponseDO responseDO = new CommonCourseResponseDO();
                    responseDO.setName(a.getName());
                    responseDO.setCourseType(a.getCourseType());
                    responseDO.setDescription(a.getDescription());
                    if (null != a.getImage()) {
                        String imageUrl = convertInputStream2Url(a.getImage());
                        responseDO.setImage(imageUrl);
                    }
                    return responseDO;
                }).collect(Collectors.toList());
        return new PullCommonCourseResponse(res);
    }

    /**
     * 获取待确认时间
     * @return
     */
    @Override
    public PullUnconfirmedResponse pullUnconfirmed() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        List<UnconfirmedResponseDO> res;
        try {
            int tutorId = updateDao.getTutorId(account);
            List<UnconfirmedDO> list = pullDao.pullUnconfirmed(tutorId);
            res = list.parallelStream()
                    .map(a -> {
                        UnconfirmedResponseDO responseDO = new UnconfirmedResponseDO();
                        responseDO.setId(a.getId());
                        responseDO.setStudentId(a.getStudentId());
                        responseDO.setStudentName(a.getUserName());
                        responseDO.setCreateTime(a.getCreateTime());
                        Calendar calendar = TimeUtils.getCalendar(a.getStartTime());
                        BookingTimeDO bookingTimeDO = new BookingTimeDO();
                        convertCalendar2BookingTime(bookingTimeDO, calendar);
                        responseDO.setBookingTimeDO(bookingTimeDO);
                        return responseDO;
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.SQL_ERROR);
        }
        return new PullUnconfirmedResponse(res);
    }

    /**
     * 获取已经预约成功的信息
     * @param request
     * @return
     */
    @Override
    public PullConfirmedResponse pullConfirmed(PullConfirmedRequest request) {
        int tutorId = updateDao.getTutorIdByName(request.getTutorName());
        List<Timestamp> list = pullDao.pullConfirmedByTutorId(tutorId);
        List<BookingTimeDO> res;
        res = list.parallelStream()
                .map(a -> {
                    BookingTimeDO bookingTimeDO = new BookingTimeDO();
                    Calendar calendar = TimeUtils.getCalendar(a);
                    convertCalendar2BookingTime(bookingTimeDO, calendar);
                    return bookingTimeDO;
                }).collect(Collectors.toList());;
        return new PullConfirmedResponse(res);
    }

    /**
     * 获取用户的所有预约信息
     * @return
     */
    @Override
    public PullBookingResponse pullBookings() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        List<BookingResponseDO> res;
        try {
            List<BookingsDO> list;
            if (userRoleDO.getRole().equals("student")) {
                list = pullDao.getStudentBookings(account);
            } else {
                list = pullDao.getTutorBookings(account);
            }
            res = list.parallelStream()
                    .map(a -> {
                        BookingResponseDO responseDO = new BookingResponseDO();
                        responseDO.setBookId(a.getBookId());
                        responseDO.setTutorName(a.getTutorName());
                        responseDO.setStudentName(a.getStudentName());
                        responseDO.setComplete(a.isComplete());
                        responseDO.setConfirm(a.isConfirm());
                        responseDO.setDelete(a.isDelete());
                        responseDO.setCreateTime(a.getCreateTime());
                        Calendar calendar = TimeUtils.getCalendar(a.getStartTime());
                        BookingTimeDO bookingTimeDO = new BookingTimeDO();
                        convertCalendar2BookingTime(bookingTimeDO, calendar);
                        responseDO.setTime(bookingTimeDO);
                        return responseDO;
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.SQL_ERROR);
        }
        return new PullBookingResponse(res);
    }

    /**
     * 获取所有老师信息
     * @return
     */
    @Override
    public PullTutorResponse pullTutors() {
        List<TutorDO> list = pullDao.getTutors();
        List<TutorResponseDO> res = list.parallelStream()
                .map(tutorDO -> {
                    TutorResponseDO tutorResponseDO = new TutorResponseDO();
                    tutorResponseDO.setAccount(tutorDO.getAccount());
                    tutorResponseDO.setUserName(tutorDO.getUserName());
                    tutorResponseDO.setAddress(tutorDO.getAddress());
                    tutorResponseDO.setPhone(tutorDO.getPhone());
                    tutorResponseDO.setTimeZone(tutorDO.getTimeZone());
                    tutorResponseDO.setBio(tutorDO.getBio());
                    tutorResponseDO.setFilePath(tutorDO.getFilePath());
                    tutorResponseDO.setImage(tutorDO.getImage() != null ? convertInputStream2Url(tutorDO.getImage()) : null);
                    tutorResponseDO.setNumber(tutorDO.getNumber());

                    // 计算 score 的平均值
                    if (tutorDO.getNumber() > 0) {
                        tutorResponseDO.setScore((float) tutorDO.getScore() / tutorDO.getNumber());
                    } else {
                        tutorResponseDO.setScore(0.0f);
                    }

                    return tutorResponseDO;
                })
                .sorted((t1, t2) -> Float.compare(t2.getScore(), t1.getScore()))
                .collect(Collectors.toList());
        // 获取课程名
        getTutorCourse(res);
        return new PullTutorResponse(res);
    }

    /**
     * admin获取所有学生信息
     * @return
     */
    @Override
    public PullStudentResponse pullStudents() {
        TokenUtils.isAdmin(tokenStorage.getToken());
        List<PullStudentDO> list = pullDao.getStudents();
        return new PullStudentResponse(list);
    }

    /**
     * 通过名字获取老师信息
     * @param request
     * @return
     */
    @Override
    public PullTutorResponse pullTutorByName(PullTutorByNameRequest request) {
        String tutorName = request.getName();
        List<TutorDO> list = pullDao.getTutorByName(tutorName);
        if (list.isEmpty()) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        List<TutorResponseDO> res = list.parallelStream()
                .map(tutorDO -> {
                    TutorResponseDO tutorResponseDO = new TutorResponseDO();
                    tutorResponseDO.setAccount(tutorDO.getAccount());
                    tutorResponseDO.setUserName(tutorDO.getUserName());
                    tutorResponseDO.setAddress(tutorDO.getAddress());
                    tutorResponseDO.setPhone(tutorDO.getPhone());
                    tutorResponseDO.setTimeZone(tutorDO.getTimeZone());
                    tutorResponseDO.setBio(tutorDO.getBio());
                    tutorResponseDO.setFilePath(tutorDO.getFilePath());
                    tutorResponseDO.setImage(tutorDO.getImage() != null ? convertInputStream2Url(tutorDO.getImage()) : null);
                    tutorResponseDO.setNumber(tutorDO.getNumber());

                    // 计算 score 的平均值
                    if (tutorDO.getNumber() > 0) {
                        tutorResponseDO.setScore((float) tutorDO.getScore() / tutorDO.getNumber());
                    } else {
                        tutorResponseDO.setScore(0.0f);
                    }

                    return tutorResponseDO;
                }).collect(Collectors.toList());
        // 获取课程名
        getTutorCourse(res);

        return new PullTutorResponse(res);
    }

    /**
     * 获取聊天用户列表
     * @return
     */
    @Override
    public PullChatListResponse pullChatList() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        List<ChatListDO> list;
        if (userRoleDO.getRole().equals("student")) {
            list = pullDao.getTutorList();
        } else {
            list = pullDao.getStudentList();
        }
        return new PullChatListResponse(list);
    }

    /**
     * 获取历史聊天记录
     * @param request
     * @return
     */
    @Override
    public PullChatInfoResponse pullChatInfo(PullChatRequest request) {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        String myName;
        String userName = request.getUserName();
        if (userRoleDO.getRole().equals("student")) {
            myName = pullDao.getStudentName(account);
        } else {
            myName = pullDao.getTutorName(account);
        }
        if (myName.equals(userName)) {
            throw new TutorManagementException(ResultCodeEnum.PARAM_ERROR);
        }
        /*
        如果没有建立过聊天，就新建chat记录
        插入假数据
         */
        int isExist1 = pullDao.isChatExist(myName, userName);
        int isExist2 = pullDao.isChatExist(userName, myName);
        if (isExist1 != 1) {
            String message1 = ChatUtils.createDefaultMsg(userName);
            String jsonStr1 = ChatUtils.createDefaultJsonStr(myName, message1);
            createChat(myName, userName, jsonStr1);
        }
        if (isExist2 != 1) {
            String message2 = ChatUtils.createDefaultMsg(myName);
            String jsonStr2 = ChatUtils.createDefaultJsonStr(userName, message2);
            createChat(userName, myName, jsonStr2);
        }

        /*
        获取历史记录，更新load_time
         */
        String msgList1 = pullDao.getHistoryMsg(myName, userName); // 获取本人发送的消息
        String msgList2 = pullDao.getHistoryMsg(userName, myName); // 获取本人收到的消息
        List<ChatDO> chatList1 = JSON.parseArray(msgList1, ChatDO.class);
        List<ChatDO> chatList2 = JSON.parseArray(msgList2, ChatDO.class);
        List<ChatDO> res = ChatUtils.mergeChatList(chatList1, chatList2);
        int loadFlag = pullDao.renewLoadTime(userName, myName); // 更新本人的加载时间

        return new PullChatInfoResponse(res);
    }

    /**
     * 获取最新消息列表
     * @return
     */
    @Override
    public PullChatInfoResponse pullNewMsg() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        String myName;
        if (userRoleDO.getRole().equals("student")) {
            myName = pullDao.getStudentName(account);
        } else {
            myName = pullDao.getTutorName(account);
        }

        /*
        获取receiver的update_time > load_time 的所有message
         */
        List<String> msgStrList = pullDao.getNewMsg(myName);

        // 使用 Stream 将 String 转换为 List<ChatDO>
        List<List<ChatDO>> listOfChatDOLists = msgStrList.stream()
                .map(msgStr -> JSON.parseArray(msgStr, ChatDO.class))
                .collect(Collectors.toList());

        // 使用 Stream 获取每个 List<ChatDO> 的最后一个 ChatDO
        List<ChatDO> res = listOfChatDOLists.stream()
                .map(chatDOList -> chatDOList.get(chatDOList.size() - 1))
                .collect(Collectors.toList());
        /*
        更新刚才新消息的load_time
         */
        if (!res.isEmpty()) {
            int updateFlag = pullDao.updateLoadTime(myName);
        }
        return new PullChatInfoResponse(res);
    }

    /**
     * 获取所有课程带token
     * @return
     */
    @Override
    public PullCourseInfoWithTokenResponse pullCourseInfoWithToken() {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        String account = userRoleDO.getAccountNumber();
        int id; // 用户id
        List<PullCourseInfoWithTokenDO> list; // 课程数据
        if (userRoleDO.getRole().equals("student")) {
            id = updateDao.getStudentId(account);
            list = pullDao.getStudentCourseInfoWithToken(id);
        } else {
            id = updateDao.getTutorId(account);
            list = pullDao.getTutorCourseInfoWithToken(id);
        }
        List<PullCourseInfoWithTokenResponseDO> res = list.parallelStream()
                .map(a -> {
                    PullCourseInfoWithTokenResponseDO responseDO = new PullCourseInfoWithTokenResponseDO();
                    responseDO.setName(a.getName());
                    responseDO.setDescription(a.getDescription());
                    responseDO.setCourseType(a.getCourseType());
                    responseDO.setChoose(!a.isChoose());
                    if (null != a.getImage()) {
                        String imageUrl = convertInputStream2Url(a.getImage());
                        responseDO.setImage(imageUrl);
                    }
                    return responseDO;
                }).collect(Collectors.toList());;

        return new PullCourseInfoWithTokenResponse(res);
    }

    /**
     * 插入到chat表中默认聊天记录
     * @param sender
     * @param receiver
     */
    private void createChat(String sender, String receiver, String msg) {
        int res = pullDao.insertIntoChat(sender, receiver, msg);
        if (res != 1) {
            throw new TutorManagementException(ResultCodeEnum.SQL_ERROR);
        }
    }

    /**
     * 填充bookingTime
     * @param bookingTimeDO
     * @param calendar
     */
    private void convertCalendar2BookingTime(BookingTimeDO bookingTimeDO, Calendar calendar) {
        bookingTimeDO.setYear(calendar.get(Calendar.YEAR));
        bookingTimeDO.setMonth(calendar.get(Calendar.MONTH) + 1);
        bookingTimeDO.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        bookingTimeDO.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        bookingTimeDO.setMinute(calendar.get(Calendar.MINUTE));
    }

    /**
     * 把数据库的图片转为url
     * @param inputStream
     * @return
     */
    private String convertInputStream2Url(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 35];
        int bytesRead = 0;

        while (true) {
            try {
                if (null == inputStream) {
                    return "data:image/png;base64,";
                }
                if ( !((bytesRead = inputStream.read(buffer)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] imageBytes = outputStream.toByteArray();
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    private void getTutorCourse(List<TutorResponseDO> list) {
        List<TutorCourseNameDO> nameDOList = pullDao.getTutorCourseList();
        // 将 nameDOList 转为 Map，以便通过 account 快速获取对应的 courseName
        Map<String, String> courseNameMap = nameDOList.stream()
                .collect(Collectors.toMap(TutorCourseNameDO::getAccount, TutorCourseNameDO::getCourseName));

        // 更新 list 中的 courseName
        list.forEach(tutorResponseDO -> {
            String account = tutorResponseDO.getAccount();
            if (courseNameMap.containsKey(account)) {
                tutorResponseDO.setCourseName(courseNameMap.get(account));
            }
        });
    }



}
