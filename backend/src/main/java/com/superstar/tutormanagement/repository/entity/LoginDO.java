package com.superstar.tutormanagement.repository.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 登录实体
 */
public class LoginDO {
    @ApiModelProperty("password")
    private String password;

    @ApiModelProperty("账号")
    private String accountNumber;
}
