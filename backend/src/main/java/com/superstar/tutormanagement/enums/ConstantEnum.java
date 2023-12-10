package com.superstar.tutormanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 一些常量的映射
 */
@Getter
@AllArgsConstructor
public enum ConstantEnum {
    STUDENT_TABLE("student", "student"),
    TUTOR_TABLE("tutor", "tutor"),
//    IMAGE_TYPE("image", "image/png"),
    ADMIN_ROLE("admin", "admin"),

    // 对应数据库字符串
    USERNAME("userName", "user_name"),
    TIMEZONE("timeZone", "time_zone"),
    FILEPATH("filePath", "file_path");

    private String type;
    private String code;

    public static ConstantEnum getEnumFromType(String type) {
        if (ENUM_MAP.containsKey(type)) {
            return ENUM_MAP.get(type);
        }
        return null;
    }

    public static boolean containsKey(String type) {
        return ENUM_MAP.containsKey(type);
    }

    private static final Map<String, ConstantEnum> ENUM_MAP = Arrays.stream(ConstantEnum.values())
            .collect(Collectors.toMap(ConstantEnum::getType, Function.identity()));
}
