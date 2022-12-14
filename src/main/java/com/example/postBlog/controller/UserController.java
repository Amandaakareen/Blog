package com.example.postBlog.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.postBlog.controller.DTO.CreateUserDTO;
import com.example.postBlog.controller.DTO.ResponseUserDTO;
import com.example.postBlog.entity.UserEntity;
import com.example.postBlog.error.EntityAlreadyExistException;
import com.example.postBlog.error.EntityDoesNotExistException ;
import com.example.postBlog.service.CommentService;
import com.example.postBlog.service.JwtService;
import com.example.postBlog.service.PostService;
import com.example.postBlog.service.UserService;


@RestController
@RequestMapping("user")
public class UserController {

    PostService postService;
    UserService userService;
    JwtService jwtService;
    CommentService  commentService;
    ModelMapper modelMapper;
    
    
    public UserController(UserService userService, PostService postService, JwtService jwtService, CommentService  commentService, ModelMapper modelMapper) {
        this.userService = userService;  
        this.postService = postService;
        this.jwtService = jwtService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDTO >> findUsers(@RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

         return ResponseEntity.ok(userService.listUsers().stream()
         .map(this ::userResponse).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseUserDTO> findUserById(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
        UserEntity user = null;
        try{
            user = userService.findUserByIdService(id);
        }catch(EntityDoesNotExistException  e){
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok().body(userResponse(user));
    } 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseUserDTO> registerUser (@RequestBody @Valid CreateUserDTO userRequest){
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
        
        return ResponseEntity.ok(userResponse(newUser));
    }
     
    @PutMapping("{id}")
    public ResponseEntity<ResponseUserDTO > editUser( @PathVariable ("id") Long id, @RequestBody @Valid CreateUserDTO userRequest, @RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
         } catch (Exception e) {
            return ResponseEntity.notFound().build();
         }

         UserEntity user = userService.findUserByIdService(id);
         Date newDate = new Date();

         user.setName(userRequest.getName());
         user.setEmail(userRequest.getEmail());
         user.setPassword(userRequest.getPassword());
         user.setUpdated(newDate); 

        try{
            userService.editUser(user);   
        }catch(EntityDoesNotExistException e){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userResponse(user));
           
    }
  
    @DeleteMapping("{id}")
    public ResponseEntity<UserEntity> deleteUserById (@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
         } catch (Exception e) {
            return ResponseEntity.notFound().build();
         }
        
        try{
          userService.findUserByIdService(id);
        }catch(EntityDoesNotExistException e){
            ResponseEntity.notFound().build();
        }
        commentService.deleteListCommentByIdUser(id);
        postService.listDeletePostByUser(id);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    private ResponseUserDTO  userResponse(UserEntity userEntity){
        return modelMapper.map(userEntity, ResponseUserDTO.class );
    }
}
