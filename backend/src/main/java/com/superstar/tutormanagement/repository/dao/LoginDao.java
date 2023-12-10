package com.superstar.tutormanagement.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superstar.tutormanagement.repository.entity.LoginDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LoginDao extends BaseMapper<LoginDO> {

    @Select("select count(*) from student where account = #{accountNumber} and password_md5 = #{password} and is_delete = FALSE")
    Integer studentLogin (@Param("accountNumber") String accountNumber, @Param("password") String password);
    @Select("select count(*) from tutor where account = #{accountNumber} and password_md5 = #{password} and is_delete = FALSE")
    Integer tutorLogin (@Param("accountNumber") String accountNumber, @Param("password") String password);
}
