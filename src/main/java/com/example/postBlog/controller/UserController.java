package com.example.postBlog.controller;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.postBlog.controller.request.UserRequest;
import com.example.postBlog.entity.PostEntity;
import com.example.postBlog.entity.UserEntity;
import com.example.postBlog.error.EntityAlreadyExistException;
import com.example.postBlog.error.EntityDoesNotExistException ;
import com.example.postBlog.service.PostService;
import com.example.postBlog.service.UserService;


@RestController
@RequestMapping("user")
public class UserController {

    PostService postService;
    UserService userService;
    
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;  
        this.postService = postService;
    }

    @GetMapping
    public List<UserEntity> findUsers(){
         return userService.listUsers();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserEntity> findUserById(@PathVariable Long id){
        UserEntity user = null;
        try{
            user = userService.findUserByIdService(id);
        }catch(EntityDoesNotExistException  e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(user);
       

    } 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserEntity> registerUser (@RequestBody @Valid UserRequest userRequest){
    
        UserEntity newUser = new UserEntity();
        Date newData = new Date();

        newUser.setName(userRequest.getName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(userRequest.getPassword());
        newUser.setRole("User");
        newUser.setCreated(newData);
        newUser.setUpdated(newData);

        try{
            userService.checkUser(newUser);
        }
        catch(EntityAlreadyExistException e){
           return ResponseEntity.badRequest().build();
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        
        return ResponseEntity.ok().build();
    }
     
    @PutMapping("{id}")
    public ResponseEntity<UserEntity> editUser( @PathVariable ("id") Long id, @RequestBody @Valid UserRequest userRequest){
        try{
            UserEntity user = userService.findUserByIdService(id);
            Date newDate = new Date();

            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setUpdated(newDate); 

            userService.editUser(user);
            
        }catch(EntityDoesNotExistException e){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
           
    }
  
    @DeleteMapping("{id}")
    public ResponseEntity<UserEntity> deleteUserById (@PathVariable Long id){
        postService.listDeletePostByUser(id);
        try{
          userService.findUserByIdService(id);
        }catch(EntityDoesNotExistException e){
            ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}