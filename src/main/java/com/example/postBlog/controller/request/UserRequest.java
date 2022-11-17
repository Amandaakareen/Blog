package com.example.postBlog.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class UserRequest {
   
    @NotEmpty @NotNull
    private String name;

    @Email
    private String email;

    @NotEmpty @NotNull @JsonIgnore
    private String password;
    
}
