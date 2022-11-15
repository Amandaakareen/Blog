package com.example.postBlog.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.postBlog.controller.request.CommentRequest;
import com.example.postBlog.entity.CommentEntity;
import com.example.postBlog.entity.PostEntity;
import com.example.postBlog.entity.UserEntity;
import com.example.postBlog.error.CommentDoesNotExistException;
import com.example.postBlog.service.CommentService;


@RestController
@RequestMapping("comentario")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @PostMapping("user/{idUser}/post/{idPost}")
    public ResponseEntity<CommentEntity> addComment(@PathVariable Long idUser,
     @PathVariable Long idPost, 
     @RequestBody CommentRequest comment){

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
        return ResponseEntity.ok(newComment);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable Long id){
        try{
            commentService.checkComment(id);
        }catch(CommentDoesNotExistException e){
            return ResponseEntity.notFound().build();
        }
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
    
}
