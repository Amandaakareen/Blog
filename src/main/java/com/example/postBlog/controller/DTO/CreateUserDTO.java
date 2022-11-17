package com.example.postBlog.controller.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class CreateUserDTO {
   
    @NotEmpty @NotNull
    private String name;

    @Email
    private String email;

    @NotEmpty @NotNull @JsonIgnore
    private String password;
    
}
