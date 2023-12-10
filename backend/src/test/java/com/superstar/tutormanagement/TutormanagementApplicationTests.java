package com.superstar.tutormanagement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superstar.tutormanagement.constant.Cipher;
import com.superstar.tutormanagement.controller.request.UpdateProfileRequest;
import com.superstar.tutormanagement.repository.entity.UserRoleDO;
import com.superstar.tutormanagement.service.UpdateService;
import com.superstar.tutormanagement.service.impl.UpdateProfileImpl;
import com.superstar.tutormanagement.utils.AESUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TutormanagementApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void test_token_2_UserRoleDO() {
        String plaintext = "";
        String cipherText = "baTF6PYB4CBrugDX9aRiHojW06i51Jc4NcCq6Du2yoZhQ5B6ZKlZ0oRJbv21Y2tn5phBKkb/ek59/9H0fnzyL129ZxBfdhTfsbLEWM3rtmWzdsEbvcVarsWz9xwzzVn1";
        try {
            plaintext = AESUtil.decrypt(cipherText, Cipher.KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = plaintext.substring(0, plaintext.indexOf("{#}"));
        JSONObject jsonObject = JSON.parseObject(json);
        UserRoleDO userRoleDO = jsonObject.toJavaObject(UserRoleDO.class);
        System.out.println(userRoleDO.getRole() + ": " + userRoleDO.getAccountNumber());
    }

    /*
    图片转Base64
     */
    @Test
    void test_fields() {
        UpdateProfileImpl updateProfile = new UpdateProfileImpl();
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setUserName("John");
        request.setAddress("123 Main St");
        request.setPhone("xsc");
        request.setTimeZone("UTC");
        request.setImage("asdadadadadasdadasasdq2314121dcsfda");
        request.setBio("A bio");
        request.setFilePath("xsdfwef34");
        String res = updateProfile.createSet("tutor", request);
        System.out.println(res);
    }

}
