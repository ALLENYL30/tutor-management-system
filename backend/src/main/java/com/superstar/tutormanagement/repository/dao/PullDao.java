package com.superstar.tutormanagement.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superstar.tutormanagement.repository.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public interface PullDao extends BaseMapper<TutorAccountDO> {

    /**
     * 获取头像
     * @param tableName
     * @param accountNumber
     * @return
     */
    @Select("select image from ${tableName} where account = #{accountNumber} and is_delete = FALSE")
    InputStream getImage (@Param("tableName") String tableName, @Param("accountNumber") String accountNumber);


    /**
     * 获取所有存在的老师的信息
     * @return
     */
    @Select("select account, password_md5, user_name from tutor where is_delete = FALSE")
    List<TutorAccountDO> getAccount ();

    /**
     * 获取老师信息
     * @param account
     * @return
     */
    @Select("select account, user_name, address, phone, time_zone, bio, file_path, image from tutor where account = #{account}")
    TutorInfoDO getTutorInfo(@Param("account") String account);

    /**
     * 获取学生信息
     * @param account
     * @return
     */
    @Select("select account, user_name, address, phone, time_zone, image from student where account = #{account}")
    StudentInfoDO getStudentInfo(@Param("account") String account);

    /**
     * 获取课程的所有信息
     * @return
     */
    @Select("select * from course")
    List<CourseDO> getCourses();

    @Select("select name, description, course_type, image from course where is_delete = FALSE")
    List<PullCourseInfoDO> getCourseInfo();

    /**
     * 学生获取已选课程
     */
    @Select("select c.name, c.description, c.course_type, c.image\n" +
            "from course c join student_course sc on c.id = sc.course_id\n" +
            "where c.is_delete = false\n" +
            "and sc.student_id = \n" +
            "(select id\n" +
            "from student s\n" +
            "where s.account = #{account})")
    List<CommonCourseDO> pullStudentCourse(@Param("account") String account);

    /**
     * 老师获取已选课程
     */
    @Select("select c.name, c.description, c.course_type, c.image\n" +
            "from course c join tutor_course tc on c.id = tc.course_id\n" +
            "where c.is_delete = false\n" +
            "and tc.tutor_id = \n" +
            "(select id\n" +
            "from tutor t\n" +
            "where t.account = #{account})")
    List<CommonCourseDO> pullTutorCourse(@Param("account") String account);

    /**
     * 老师获取未确认订单
     * @param tutorId
     * @return
     */
    @Select("select b.id, b.student_id, s.user_name, b.start_time, b.create_time " +
            "from book b join student s on b.student_id = s.id " +
            "where b.tutor_id = #{tutorId} " +
            "and b.is_confirm = FALSE and b.is_delete = FALSE")
    List<UnconfirmedDO> pullUnconfirmed(@Param("tutorId") int tutorId);

    /**
     * 学生获取老师的已预约时间
     * @param tutorId
     * @return
     */
    @Select("select start_time from book where tutor_id = #{tutorId} and is_confirm = TRUE and is_complete = FALSE and is_delete = FALSE")
    List<Timestamp> pullConfirmedByTutorId(@Param("tutorId") int tutorId);

    /**
     * 学生的预约信息
     * @param account
     * @return
     */
    @Select("select b.id as book_id, t.user_name as tutor_name, s.user_name as student_name, b.is_confirm, b.is_complete, b.is_delete, b.start_time, b.create_time " +
            "from book b join student s on b.student_id = s.id " +
            "join tutor t on b.tutor_id = t.id " +
            "where s.account = #{account}")
    List<BookingsDO> getStudentBookings(@Param("account") String account);

    /**
     * 老师获取预约信息
     * @param account
     * @return
     */
    @Select("select b.id as book_id, t.user_name as tutor_name, s.user_name as student_name, b.is_confirm, b.is_complete, b.is_delete, b.start_time, b.create_time " +
            "from book b join student s on b.student_id = s.id " +
            "join tutor t on b.tutor_id = t.id " +
            "where t.account = #{account}")
    List<BookingsDO> getTutorBookings(@Param("account") String account);

    /**
     * 学生获取老师信息
     * @return
     */
    @Select("select t.account, t.user_name, t.address, t.phone, t.time_zone, t.bio, t.file_path, t.image, s.score, s.number " +
            "from tutor t join score s on t.id = s.tutor_id")
    List<TutorDO> getTutors();

    /**
     * 获取所有学生信息
     * @return
     */
    @Select("select account, password_MD5, user_name, address, time_zone, is_delete from student")
    List<PullStudentDO> getStudents();

    /**
     * 通过名字获取老师信息
     * @param tutorName
     * @return
     */
    @Select("select t.account, t.user_name, t.address, t.phone, t.time_zone, t.bio, t.file_path, t.image, s.score, s.number " +
            "from tutor t join score s on t.id = s.tutor_id " +
            "where t.user_name = #{tutorName}")
    List<TutorDO> getTutorByName(@Param("tutorName") String tutorName);

    /**
     * 学生获取聊天列表
     * @return
     */
    @Select("select id as user_id, user_name from tutor where is_delete = FALSE")
    List<ChatListDO> getTutorList();

    /**
     * 老师获取聊天列表
     * @return
     */
    @Select("select id as user_id, user_name from student where is_delete = FALSE")
    List<ChatListDO> getStudentList();

    /**
     * 获取学生的名字
     * @param account
     * @return
     */
    @Select("select user_name from student where account = #{account}")
    String getStudentName(@Param("account") String account);

    /**
     * 获取老师的名字
     * @param account
     * @return
     */
    @Select("select user_name from tutor where account = #{account}")
    String getTutorName(@Param("account") String account);

    /**
     * 查询是否存在这样的发送者接收者记录
     * @param sender
     * @param receiver
     * @return
     */
    @Select("select count(*) from chat where sender = #{sender} and receiver = #{receiver}")
    Integer isChatExist(@Param("sender") String sender, @Param("receiver") String receiver);

    /**
     * 插入新记录到chat表中
     * @param sender
     * @param receiver
     * @param msg
     * @return
     */
    @Insert("INSERT INTO chat (sender, receiver, message) " +
            "VALUES (#{sender}, #{receiver}, #{msg});")
    Integer insertIntoChat(@Param("sender") String sender, @Param("receiver") String receiver, @Param("msg") String msg);

    /**
     * 获取历史聊天记录
     * @param sender
     * @param receiver
     * @return
     */
    @Select("select message from chat where sender = #{sender} and receiver = #{receiver}")
    String getHistoryMsg(@Param("sender") String sender, @Param("receiver") String receiver);


    /**
     * 更新接收方的load_time
     * @param sender
     * @param receiver
     * @return
     */
    @Update("UPDATE chat " +
            "SET load_time = CURRENT_TIMESTAMP " +
            "WHERE sender = #{sender} AND receiver = #{receiver}")
    int renewLoadTime(@Param("sender") String sender, @Param("receiver") String receiver);

    /**
     * 获取receiver的最新消息
     * @param receiver
     * @return
     */
    @Select("select message from chat where update_time > load_time and receiver = #{receiver}")
    List<String> getNewMsg(@Param("receiver") String receiver);

    /**
     * 更新收到新消息后的load_time
     * @param receiver
     * @return
     */
    @Update("update chat " +
            "SET load_time = CURRENT_TIMESTAMP " +
            "WHERE update_time > load_time AND receiver = #{receiver}")
    int updateLoadTime(@Param("receiver") String receiver);

    /**
     * 学生获取所有课程带token
     * @param id
     * @return
     */
    @Select("SELECT " +
            "    c.name, " +
            "    c.description, " +
            "    c.course_type, " +
            "    c.image, " +
            "    COALESCE(sc.is_delete, true) AS is_choose " +
            "FROM " +
            "    course c " +
            "LEFT JOIN " +
            "    student_course sc ON c.id = sc.course_id AND sc.student_id = #{id};")
    List<PullCourseInfoWithTokenDO> getStudentCourseInfoWithToken(@Param("id") int id);

    /**
     * 老师获取所有课程带token
     * @param id
     * @return
     */
    @Select("SELECT " +
            "    c.name, " +
            "    c.description, " +
            "    c.course_type, " +
            "    c.image, " +
            "    COALESCE(tc.is_delete, true) AS is_choose " +
            "FROM " +
            "    course c " +
            "LEFT JOIN " +
            "    tutor_course tc ON c.id = tc.course_id AND tc.tutor_id = #{id};")
    List<PullCourseInfoWithTokenDO> getTutorCourseInfoWithToken(@Param("id") int id);

    /**
     * 获取老师所有选课
     * @return
     */
    @Select("select t.account, group_concat(c.name) as course_name " +
            "from tutor_course tc join course c on tc.course_id = c.id " +
            "join tutor t on tc.tutor_id = t.id " +
            "group by t.account")
    List<TutorCourseNameDO> getTutorCourseList();
}
