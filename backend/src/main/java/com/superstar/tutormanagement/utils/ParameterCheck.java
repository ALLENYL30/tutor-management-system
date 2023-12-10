package com.superstar.tutormanagement.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数校验工具
 */
public class ParameterCheck {

    /**
     * 邮箱校验
     * @param str
     * @return
     */
    public static boolean emailCheck(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        String pattern =   "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w]+)*(\\.[\\w]{2,})$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 图像文件检查
     * @param file
     * @return
     */
    public static boolean imageCheck(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).startsWith("image/");
    }
}
