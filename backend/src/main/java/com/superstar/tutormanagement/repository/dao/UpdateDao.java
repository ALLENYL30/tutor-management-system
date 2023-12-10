package com.superstar.tutormanagement.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superstar.tutormanagement.repository.entity.ScoreDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;

public interface UpdateDao extends BaseMapper {
    /**
     * 修改个人资料
     * @param table
     * @param setValue
     * @param account
     * @return
     */
    @Update("update ${table} " +
            "set ${setValue} update_time = CURRENT_TIMESTAMP " +
            "where account = #{account}")
    Integer updateProfile(@Param("table") String table, @Param("setValue") String setValue, @Param("account") String account);

    @Update("update ${table} " +
            "set ${setValue} image = #{image}, update_time = CURRENT_TIMESTAMP " +
            "where account = #{account}")
    Integer updateProfileWithImage(@Param("table") String table, @Param("setValue") String setValue,
                                   @Param("image") byte[] image, @Param("account") String account);

    @Select("select id from course where name = #{name} and is_delete = FALSE")
    Integer getCourseId(@Param("name") String name);

    @Select("select id from student where account = #{account}")
    Integer getStudentId(@Param("account") String account);

    @Select("select id from tutor where user_name = #{name}")
    Integer getTutorIdByName(@Param("name") String name);

    @Select("select id from tutor where account = #{account}")
    Integer getTutorId(@Param("account") String account);

    /**
     * 学生选课
     * @param courseId
     * @param studentId
     * @return
     */
    @Insert("INSERT INTO student_course (course_id, student_id, create_time, update_time, is_delete)\n" +
            "VALUES (#{courseId}, #{studentId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE)")
    Integer studentChooseCourse(@Param("courseId") int courseId, @Param("studentId") int studentId);

    /**
     * 老师选课
     * @param courseId
     * @param tutorId
     * @return
     */
    @Insert("INSERT INTO tutor_course (course_id, tutor_id, create_time, update_time, is_delete)\n" +
            "VALUES (#{courseId}, #{tutorId}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE)")
    Integer tutorChooseCourse(@Param("courseId") int courseId, @Param("tutorId") int tutorId);

    /**
     * 学生预约老师
     * @param tutorId
     * @param studentId
     * @param timestamp
     * @return
     */
    @Insert("INSERT INTO book (tutor_id, student_id, start_time, create_time, update_time)\n" +
            "VALUES\n" +
            "  (#{tutorId}, #{studentId}, #{time}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    Integer studentBook(@Param("tutorId") int tutorId, @Param("studentId") int studentId, @Param("time") Timestamp timestamp);

    /**
     * 老师确认预约
     * @param bookingId
     * @return
     */
    @Update("update book " +
            "set is_confirm = TRUE " +
            "where id = #{bookingId} and is_delete = FALSE")
    int confirmBookingById(@Param("bookingId") int bookingId);

    /**
     * 学生确认完成订单
     * @param bookingId
     * @return
     */
    @Update("update book " +
            "set is_complete = TRUE " +
            "where id = #{bookingId} " +
            "and is_confirm = TRUE and is_delete = FALSE")
    int studentCompleteBooking(@Param("bookingId") int bookingId);

    /**
     * 获取老师的id
     * @param bookId
     * @return
     */
    @Select("select tutor_id from book where id = #{bookId}")
    Integer getTutorIdByBooking(@Param("bookId") int bookId);

    /**
     * 获取分数
     * @param tutorId
     * @return
     */
    @Select("select score, number from score where tutor_id = #{tutorId}")
    ScoreDO getScore(@Param("tutorId") int tutorId);

    /**
     * 更新分数
     * @param tutorId
     * @param score
     * @param number
     * @return
     */
    @Update("UPDATE score " +
            "SET score = #{score}, number = #{number} " +
            "WHERE tutor_id = #{tutorId}")
    int setScore(int tutorId, int score, int number);

    /**
     * 更新消息
     * @param sender
     * @param receiver
     * @param newMsg
     * @return
     */
    @Update("Update chat " +
            "SET message = #{newMsg}, update_time = CURRENT_TIMESTAMP " +
            "WHERE sender = #{sender} and receiver = #{receiver}")
    int updateMsg(@Param("sender") String sender, @Param("receiver") String receiver, @Param("newMsg") String newMsg);
}
