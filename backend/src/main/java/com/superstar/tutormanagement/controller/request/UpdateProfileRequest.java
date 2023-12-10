package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    private String userName;
    private String address;
    private String phone;
    private String timeZone;
    private String image;
    private String bio;
    private String filePath;
}
