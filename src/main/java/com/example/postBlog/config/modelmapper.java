package com.example.postBlog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.postBlog.controller.DTO.ResponsePostDTO;
import com.example.postBlog.entity.PostEntity;
@Configuration
public class modelmapper {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    
}
