package com.superstar.tutormanagement;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CommonTest {
    @Test
    public void test1() {
        System.out.println("".isEmpty());
    }

    @Test
    public void test2() {
        // 要转换的字符串日期
        String dateString = "2023-11-10 15:30:00";

        // 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // 将字符串解析为日期
            Date date = dateFormat.parse(dateString);

            // 使用Calendar类获取年、月、日、时
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，所以要加1
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // 打印结果
            System.out.println("年: " + year);
            System.out.println("月: " + month);
            System.out.println("日: " + day);
            System.out.println("时: " + hour);
            System.out.println("分: " + minute);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        String[] dateStrings = {
                "2023-11-12 15:30:00",
                "2023-11-12 10:30:00",
                "2023-11-12 18:45:00"
        };

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将字符串转换为 LocalDateTime 对象
        LocalDateTime[] dateTimes = Arrays.stream(dateStrings)
                .map(str -> LocalDateTime.parse(str, formatter))
                .toArray(LocalDateTime[]::new);

        // 对 LocalDateTime 对象进行排序
        Arrays.sort(dateTimes);

        // 打印排序后的结果
        for (LocalDateTime dateTime : dateTimes) {
            System.out.println(dateTime.format(formatter));
        }
    }

    @Test
    public void test4() {
        // 获取当前的 LocalDateTime 对象
        LocalDateTime currentDateTime = LocalDateTime.now();

        System.out.println("Current LocalDateTime: " + currentDateTime);
    }
}
