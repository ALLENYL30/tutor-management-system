package com.superstar.tutormanagement.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superstar.tutormanagement.constant.Cipher;
import com.superstar.tutormanagement.enums.ConstantEnum;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.repository.entity.UserRoleDO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 包含了token的操作
 */
public class TokenUtils {

    /**
     * 得到json的string
     * @param obj
     * @return
     */
    private String getJsonStr(Object obj) {
        JSON json = (JSON) JSON.toJSON(obj);
        return json.toJSONString();
    }

    /**
     * json-->md5密文
     * @param obj
     * @return
     */
    private String json2MD5(Object obj) {
        return MD5.saltMD5Encrypt(getJsonStr(obj));
    }

    /**
     * 组合json.md5
     * @param object
     * @return
     */
    private String combination(Object object) {
        String json = getJsonStr(object);
        String md5 = json2MD5(object);
        return json + "{#}" + md5;
    }

    /**
     * 获取token
     * @param o
     * @return
     */
    public static String getToken(Object o) {
        TokenUtils tokenUtils = new TokenUtils();
        try {
            return AESUtil.encrypt(tokenUtils.combination(o), Cipher.KEY);
        } catch (Exception e) {
           throw new TutorManagementException(ResultCodeEnum.ENCRYPT_ERROR);
        }
    }


    /**
     * 返回token是否合法
     * @param cipherText
     * @return
     */
    public static boolean isValid(String cipherText) {
        String plaintext = "";
        try {
            plaintext = AESUtil.decrypt(cipherText, Cipher.KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = plaintext.substring(0, plaintext.indexOf("{#}"));
        String md5 = plaintext.substring(plaintext.indexOf("{#}") + 3);
        return md5.equals(MD5.saltMD5Encrypt(json));
    }

    /**
     * 解析token，返回身份信息 UserRoleDO
     * @param
     * @return
     */
    public static UserRoleDO decryptToken(String token) {
//        String token = tokenStorage.getToken();
        String plaintext = "";
        try {
            plaintext = AESUtil.decrypt(token, Cipher.KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = plaintext.substring(0, plaintext.indexOf("{#}"));

        UserRoleDO userRoleDO = JSON.parseObject(json).toJavaObject(UserRoleDO.class);
        return userRoleDO;
    }

    /**
     * 不是admin就报错
     * @param token
     */
    public static void isAdmin(String token) {
        UserRoleDO userRoleDO = decryptToken(token);
        if (!userRoleDO.getRole().equals(ConstantEnum.getEnumFromType("admin").getCode())) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }
    }


}
