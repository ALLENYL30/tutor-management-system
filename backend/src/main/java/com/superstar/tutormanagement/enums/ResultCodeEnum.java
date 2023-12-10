package com.superstar.tutormanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码enum类
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    // 成功代码
    SUCCESS("200", "success"),

    LOGIN_FAILED("400" ,"invalid input"),
    INVALID_FILE("400", "invalid file"),
    ACCOUNT_ERROR("400","account already exist"),
    MAX_SIZE_ERROR("M0000", "image exceeds its maximum size"),
    PARAM_ERROR("400", "invalid parameter"),
    ENCRYPT_ERROR("400", "encrypt fail"),
    DECRYPT_ERROR("400", "decrypt fail"),
    TOKEN_ERROR("400", "token is invalid"),
    TOKEN_EMPTY("400", "token is empty"),
    TYPE_ERROR("400", "course type must has , "),
    COURSE_EXIST("400", "course name already exist"),
    COURSE_EMPTY("400", "don't have this course or invalid input"),
    CHOOSE_COURSE_FAILED("400", "already choose this course"),
    TIMESTAMP_ERROR("400", "timestamp error"),
    SQL_ERROR("400", "sql error"),
    BOOKING_COMPLETE_ERROE("400", "booking is not confirm or already delete"),

    //token相关
    IDENTITY_VERIFICATION_FAILED("A0000", "token不合法"),
    NO_TOKEN_IN_REQUEST("A0001", "请求头未包含token"),
    TOKEN_FORMAT_ERROR("A0002", "token格式错误"),


    // 失败代码
    QUERY_EMPTY("A0010", "数据库查询不到数据"),
    ILLEGAL_PARAM("A0011", "参数校验失败"),

    REDIS_POOL_FAILED("B0001", "redis连接池执行错误"),
    REDIS_DELETE_FAILED("B0002", "redis删除失败"),
    REDIS_SET_FAILED("B0003", "redis添加失败"),
    REDIS_GET_FAILED("B0004", "redis获取失败"),
    REDIS_GET_LEFT_TIME_FAILED("B0005", "redis获取剩余时间失败"),

    REQUEST_FREQUENT("C0001", "接口请求过于频繁"),

    ACCESS_FORBIDDEN("D0001", "无权限访问"),

    ENCRYPT_FAILED("E0001", "md5 failed");

    private final String code;
    private final String message;

}
