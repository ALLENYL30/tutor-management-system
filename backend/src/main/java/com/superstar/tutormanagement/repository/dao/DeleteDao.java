package com.superstar.tutormanagement.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DeleteDao extends BaseMapper {
    /**
     * 逻辑删除
     * @param name
     * @return
     */
    @Update("update course set is_delete = True where name = #{name}")
    Integer logicallyDelete(@Param("name") String name);

    /**
     * 物理删除
     * @param name
     * @return
     */
    @Delete("delete from course where name = #{name}")
    Integer physicalDelete(@Param("name") String name);

    @Select("select id from student where account = #{account}")
    Integer getStudentId(@Param("account") String account);

    @Select("select id from course where name = #{name}")
    Integer getCourseId(@Param("name") String name);

    @Delete("delete from student_course where student_Id = #{studentId} and course_Id = #{courseId}")
    Integer physicalDelete(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    /**
     * 获取老师id
     * @param account
     * @return
     */
    @Select("select id from tutor where account = #{account}")
    int getTutorId(@Param("account") String account);

    /**
     * 删除老师选课
     * @param tutorId
     * @param courseId
     * @return
     */
    @Delete("delete from tutor_course where tutor_id = #{tutorId} and course_id = #{courseId}")
    Integer deleteTutorSelectedCourse(int tutorId, int courseId);

    /**
     * 取消预约
     * @param bookId
     * @return
     */
    @Update("update book " +
            "set is_delete = TRUE " +
            "where id = #{bookId}")
    Integer deleteBook(@Param("bookId") int bookId);
}
