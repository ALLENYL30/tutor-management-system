package com.superstar.tutormanagement.service.impl;

import com.superstar.tutormanagement.controller.request.UploadCourseRequest;
import com.superstar.tutormanagement.controller.response.UploadImageResponse;
import com.superstar.tutormanagement.enums.ConstantEnum;
import com.superstar.tutormanagement.enums.ResultCodeEnum;
import com.superstar.tutormanagement.exception.TutorManagementException;
import com.superstar.tutormanagement.repository.dao.UploadDao;
import com.superstar.tutormanagement.repository.entity.UserRoleDO;
import com.superstar.tutormanagement.service.UploadService;
import com.superstar.tutormanagement.utils.ParameterCheck;
import com.superstar.tutormanagement.utils.TokenStorage;
import com.superstar.tutormanagement.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Base64;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private TokenStorage tokenStorage;

    @Resource
    private UploadDao uploadDao;

    private static final int IMAGE_SIZE = 200 * 1024;

    @Override
    public UploadImageResponse uploadImage(MultipartFile file) {
        String base64Image;
        try {
            if (file.getSize() > IMAGE_SIZE) {
                throw new TutorManagementException(ResultCodeEnum.MAX_SIZE_ERROR);
            }

            // 判断文件是否为图片
            if (!ParameterCheck.imageCheck(file)) {
                throw new TutorManagementException(ResultCodeEnum.INVALID_FILE);
            }

            // 返回字节数组形式的图片数据
            byte[] imageBytes = file.getBytes();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new TutorManagementException(ResultCodeEnum.INVALID_FILE);
        }
        return new UploadImageResponse(base64Image);
    }

    @Override
    public Integer uploadCourse(UploadCourseRequest request) {
        UserRoleDO userRoleDO = TokenUtils.decryptToken(tokenStorage.getToken());
        if (!userRoleDO.getRole().equals(ConstantEnum.getEnumFromType("admin").getCode())) {
            throw new TutorManagementException(ResultCodeEnum.LOGIN_FAILED);
        }
        if (uploadDao.isCourseExist(request.getName()) != 0) {
            throw new TutorManagementException(ResultCodeEnum.COURSE_EXIST);
        }
        if (!isValidCommaSeparatedString(request.getCourseType())) {
            throw new TutorManagementException(ResultCodeEnum.TYPE_ERROR);
        }
        byte[] image = Base64.getDecoder().decode(request.getImage());
        int res = uploadDao.uploadCourse(request.getName(), request.getCourseType(), request.getDescription(), image);
        return res;
    }

    /**
     * 判断是否是逗号分割的
     * @param input
     * @return
     */
    private boolean isValidCommaSeparatedString(String input) {
        // 使用正则表达式来匹配只包含英文逗号或者不包含逗号的字符串
        String pattern = "^[,]*$|^[a-zA-Z]+(,[a-zA-Z]+)*$";
        return input.matches(pattern);
    }
}
