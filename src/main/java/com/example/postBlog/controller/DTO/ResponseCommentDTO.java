package com.example.postBlog.controller.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class ResponseCommentDTO {
    @NotBlank
    private String content; 
}
