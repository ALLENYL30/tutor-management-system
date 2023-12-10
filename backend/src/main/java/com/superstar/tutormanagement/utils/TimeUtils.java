package com.superstar.tutormanagement.utils;

import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    /**
     * 把时间字符串转为timestamp
     * @param date
     * @return
     */
    public static Timestamp getTime(String date) {
        // 解析时间字符串
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp;
        try {
            Date parsedDate = dateFormat.parse(date);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }
        return timestamp;
    }

    /**
     * 获取Calender对象
     * @param timestamp
     * @return
     */
    public static Calendar getCalendar(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(timestamp.getTime());
        } catch (Exception e) {
            throw new TutorManagementException(ResultCodeEnum.TIMESTAMP_ERROR);
        }
        return calendar;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
