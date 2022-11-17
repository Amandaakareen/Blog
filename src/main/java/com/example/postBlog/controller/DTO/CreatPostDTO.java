package com.example.postBlog.controller.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreatPostDTO {

    @NotBlank
    private String title; 
    @NotBlank
    private String  content;

    
}
