package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import javax.validation.constraints.*;

@Data
public class User {

    private Integer userId;

    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$",message = "手机号码格式不正确")
    @NotNull(message = "手机号不能为空")
    private String tel;
    private String username;

    @Size(min = 6, max = 15, message = "密码长度必须在6到15位之间")
    @NotNull(message = "密码不能为空")
    @JsonIgnore
    private String password;
    private String avatar;

    @NotNull(message = "角色不能为空")
    @Min(value = 1, message = "角色值不正确")
    @Max(value = 2, message = "角色值不正确")
    private Integer role;
}
