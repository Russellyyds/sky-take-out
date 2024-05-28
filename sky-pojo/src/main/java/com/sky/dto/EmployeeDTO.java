package com.sky.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Long id;
    @NotBlank(message = "username不能为空")
    private String username;
    @NotBlank(message = "name不能为空")
    private String name;

    private String phone;

    private String sex;
    @Pattern(regexp = "\\d{15}|\\d{18}", message = "身份证号码格式不正确")
    private String idNumber;

}
