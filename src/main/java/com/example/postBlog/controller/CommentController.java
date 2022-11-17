package com.example.postBlog.controller;

import java.util.Date;


import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.postBlog.controller.DTO.CreatCommentDTO;
import com.example.postBlog.controller.DTO.ResponseCommentDTO;
import com.example.postBlog.entity.CommentEntity;
import com.example.postBlog.entity.PostEntity;
import com.example.postBlog.entity.UserEntity;
import com.example.postBlog.error.CommentDoesNotExistException;
import com.example.postBlog.service.CommentService;
import com.example.postBlog.service.JwtService;


@RestController
@RequestMapping("comentario")
public class CommentController {

    CommentService commentService;
    JwtService jwtService;
    ModelMapper modelMapper;

    public CommentController(CommentService commentService,  JwtService jwtService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.jwtService =  jwtService;
        this.modelMapper = modelMapper;
    }
    
    @PostMapping("user/{idUser}/post/{idPost}")
    public ResponseEntity<ResponseCommentDTO> addComment(@PathVariable Long idUser,
     @PathVariable Long idPost, 
     @RequestBody CreatCommentDTO comment, @RequestHeader("Authorization") String 
     jwt){
        try {
            jwtService.checkToken(jwt);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        CommentEntity newComment = new CommentEntity();
        UserEntity newUser = new UserEntity();
        PostEntity newPost = new PostEntity();
        Date newDate = new Date();
        
        newUser.setId(idUser);
        newPost.setId(idPost);

        newComment.setContent(comment.getContent());
        newComment.setUser(newUser);
        newComment.setPost(newPost);
        newComment.setCreated(newDate);
        newComment.setUpdated(newDate);
        
        commentService.addComment(newComment);
        return ResponseEntity.ok(commentResponse(newComment));

    }
    @DeleteMapping("{id}")
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        try{
            commentService.checkComment(id);
        }catch(CommentDoesNotExistException e){
            return ResponseEntity.notFound().build();
        }
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseCommentDTO> editComment(@PathVariable Long id,@RequestHeader("Authorization") String jwt){
        try {
            jwtService.checkToken(jwt);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        CommentEntity comment = null;       
        try{
         comment = commentService.checkComment(id); 
       }catch(CommentDoesNotExistException e){
        return ResponseEntity.notFound().build();
       }
       commentService.addComment(comment);
       return ResponseEntity.ok(commentResponse(comment));

    }




    private ResponseCommentDTO  commentResponse(CommentEntity CommentEntity){
        return modelMapper.map(CommentEntity , ResponseCommentDTO.class);


    }
    
}
