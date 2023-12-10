package com.superstar.tutormanagement.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RegisterDao extends BaseMapper {

    @Select("select count(*) from student where account = #{account}")
    Integer isStudentExist (@Param("account") String account);

    @Select("select count(*) from tutor where account = #{account}")
    Integer isTutorExist (@Param("account") String account);

    @Insert("INSERT INTO student (account, password_md5, user_name, create_time, update_time, address, phone, time_zone, is_delete, image)\n" +
            "VALUES (#{account}, #{password}, #{userName}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, #{address}, #{phone}, #{timeZone}, false, #{image})")
    Integer studentRegister(@Param("account") String account, @Param("password") String password, @Param("userName") String name,
                            @Param("address") String address, @Param("phone") String phone,
                            @Param("timeZone") String timeZone, @Param("image") byte[] image);


    @Insert("INSERT INTO tutor (account, password_md5, user_name, create_time, update_time, address, phone, time_zone, is_delete, image, bio, file_path)\n" +
            "VALUES (#{account}, #{password}, #{userName}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, #{address}, #{phone}, #{timeZone}, false, #{image}, #{bio}, #{filePath})")
    Integer tutorRegister(@Param("account") String account, @Param("password") String password, @Param("userName") String name,
                          @Param("address") String address, @Param("phone") String phone,
                          @Param("timeZone") String timeZone, @Param("image") byte[] image,
                          @Param("bio") String bio, @Param("filePath") String filePath);

    @Insert("INSERT INTO score (tutor_id) VALUES (#{tutorId})")
    Integer register2Score(@Param("tutorId") int tutorId);
}
