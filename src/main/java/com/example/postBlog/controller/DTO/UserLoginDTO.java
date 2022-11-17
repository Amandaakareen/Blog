package com.example.postBlog.controller.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginDTO {
    @Email
    private String email;
    @NotBlank
    private String password;
    
    
}
