package com.superstar.tutormanagement.repository.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRoleDO {
    @ApiModelProperty("role")
    private String role;

    @ApiModelProperty("account")
    private String accountNumber;
}
