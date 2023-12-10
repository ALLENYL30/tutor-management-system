package com.superstar.tutormanagement.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取个人信息response
 */
public class PullProfileResponse {
    private String account;
    private String userName;
    private String address;
    private String phone;
    private String timeZone;
    private String bio;
    private String filePath;
    private String image;

    public PullProfileResponse(String account, String userName, String address, String phone,
                               String timeZone, String image) {
        this.account = account;
        this.userName = userName;
        this.address = address;
        this.phone = phone;
        this.timeZone = timeZone;
        this.image = image;
    }
}
