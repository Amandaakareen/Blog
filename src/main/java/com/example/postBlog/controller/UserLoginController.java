package com.example.postBlog.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.postBlog.controller.DTO.UserLoginRequest;
import com.example.postBlog.error.EntityDoesNotExistException;
import com.example.postBlog.service.UserService;

@RestController
@RequestMapping("login")
public class UserLoginController {
    
    UserService userService;
    
    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping 
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginRequest userLoginRequest){
        String newJwt = null;
        try{
            newJwt =  userService.loginUser(userLoginRequest.getEmail(),userLoginRequest.getPassword());
        }catch(EntityDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }    
        return ResponseEntity.ok(newJwt);
    }


}
