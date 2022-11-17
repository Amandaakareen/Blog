package com.example.postBlog.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.postBlog.controller.DTO.CreatPostDTO;
import com.example.postBlog.controller.DTO.ResponsePostDTO;
import com.example.postBlog.entity.PostEntity;
import com.example.postBlog.entity.UserEntity;
import com.example.postBlog.error.EntityDoesNotExistException;
import com.example.postBlog.error.PostDoesNotExistException;
import com.example.postBlog.service.CommentService;
import com.example.postBlog.service.JwtService;
import com.example.postBlog.service.PostService;
import com.example.postBlog.service.UserService;

import lombok.extern.slf4j.Slf4j;




@RestController
@RequestMapping("post")
@Slf4j
public class PostController {
    
    PostService postService;
    UserService userService;
    JwtService jwtService;
    CommentService commentService;
    ModelMapper modelMapper;

    public PostController(PostService postService, UserService userService, JwtService jwtService, CommentService commentService, ModelMapper modelMapper) {
        this.postService = postService;
        this.userService= userService;
        this.jwtService =  jwtService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;

    }
    @GetMapping
    public ResponseEntity< List<ResponsePostDTO>> listAllPosts(@RequestHeader("Authorization") String jwt){
        
        try {
          jwtService.checkToken(jwt);
       } catch (Exception e) {
          return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(postService.findAllPosts().stream().map(this::postResponse).collect(Collectors.toList()));
    }
    @GetMapping("{id}")
    public ResponseEntity<ResponsePostDTO> findPostById(@PathVariable Long id,@RequestHeader("Authorization") String jwt){
        
        try {
          jwtService.checkToken(jwt);
       } catch (Exception e) {
          return ResponseEntity.notFound().build();
       }
        PostEntity post = null;
        try{
            post = postService.findPostById(id);
        }catch (PostDoesNotExistException e) {

            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(postResponse(post));
    }

    @GetMapping("user/{id}")
    public ResponseEntity< List<ResponsePostDTO>> listAllPostsDoUser(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        try {
          jwtService.checkToken(jwt);
       } catch (Exception e) {
          return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(postService.findAllPostsByUser(id).stream().map(this :: postResponse).collect(Collectors.toList()));
    }
    
    @PostMapping("{id}")
    public ResponseEntity<ResponsePostDTO> addPost(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long id, 
        @RequestBody @Valid CreatPostDTO postRequest
     ){ 
        
         try {
           jwtService.checkToken(jwt);
        } catch (Exception e) {
           return ResponseEntity.notFound().build();
        }
        UserEntity userPost = null;
        try{ userPost = userService.findUserByIdService(id);
        }catch(EntityDoesNotExistException  e){
        return ResponseEntity.badRequest().build();
        }

        PostEntity newPost = new PostEntity();
        Date newDate = new Date();

        newPost.setTitle(postRequest.getTitle());
        newPost.setContent(postRequest.getContent());
        newPost.setUser(userPost);
        newPost.setCreated(newDate);
        newPost.setUpdated(newDate);

        postService.addPost(newPost);
        
        return ResponseEntity.ok(postResponse(newPost));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponsePostDTO> editPost( @PathVariable Long id ,@RequestBody @Valid CreatPostDTO postRequest,  @RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
         } catch (Exception e) {
            return ResponseEntity.notFound().build();
         }
        PostEntity post = null;
        Date newDate = new Date();
        try {
            post = postService.findPostById(id);
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setUpdated(newDate);
        } catch (PostDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
        postService.editPost(post);    
        return ResponseEntity.ok(postResponse(post));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<PostEntity> deletePost(
        @PathVariable Long id,
        @RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        
        
        commentService.deleteListCommentByIdPost(id);

        try{
        postService.deletePostById(id);
        }catch (PostDoesNotExistException e) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
    private ResponsePostDTO  postResponse(PostEntity postEntity){
        return modelMapper.map(postEntity, ResponsePostDTO.class);


    }


    
}
