package com.superstar.tutormanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.superstar.tutormanagement.repository.dao"})
public class TutorManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutorManagementApplication.class, args);
    }

}
