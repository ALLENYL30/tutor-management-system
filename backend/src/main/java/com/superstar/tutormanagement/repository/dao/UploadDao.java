package com.superstar.tutormanagement.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UploadDao extends BaseMapper {

    /**
     * 上传课程信息
     * @param name
     * @param courseType
     * @param description
     * @param image
     * @return
     */
    @Insert("INSERT INTO course (name, course_type, description, create_time, update_time, is_delete, image) VALUES\n" +
            "    (#{name}, #{courseType}, #{description}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,#{image})")
    Integer uploadCourse(@Param("name") String name, @Param("courseType") String courseType,
                         @Param("description") String description, @Param("image") byte[] image);

    @Select("select count(*) from course where name = #{name} and is_delete = FALSE")
    Integer isCourseExist(@Param("name") String name);
}
