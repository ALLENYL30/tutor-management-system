package com.superstar.tutormanagement.utils;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5 {
    /**
     * 返回MD5加密文本
     * @param msg
     * @return
     */
    public static String encrypt(String msg) {
        String md5Str = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(msg.getBytes(StandardCharsets.UTF_8));
            md5Str = ByteArrayUtil.toHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new TutorManagementException(ResultCodeEnum.ENCRYPT_FAILED);
        }
        return md5Str;
    }

    /**
     * 加盐，返回加盐后的字符串
     * @param msg
     * @return
     */
    private static String addSalt(String msg) {
        int salt = msg.hashCode() >>> 16;
        return msg + salt;
    }

    /**
     * 加盐+md5加密
     * @param msg
     * @return
     */
    public static String saltMD5Encrypt(String msg) {
        return encrypt(addSalt(msg));
    }
}
