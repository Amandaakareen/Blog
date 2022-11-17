package com.example.postBlog.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginRequest {
    @Email
    private String email;
    @NotBlank
    private String password;
    
    
}
