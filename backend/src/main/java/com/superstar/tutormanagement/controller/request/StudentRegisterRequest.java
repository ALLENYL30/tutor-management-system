package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegisterRequest {
    @NonNull
    private String account;

    @NonNull
    private String userName;

    @NonNull
    private String password;

    @NonNull
    private String address;

    @NonNull
    private String phone;

    @NonNull
    private String timeZone;

    @NonNull
    private String image;

}
