package com.example.postBlog.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PostRequest {

    @NotBlank
    private String title; 
    @NotBlank
    private String  content;

    
}
