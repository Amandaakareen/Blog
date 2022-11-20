package com.example.postBlog.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.postBlog.entity.CommentEntity;
import com.example.postBlog.error.CommentDoesNotExistException;
import com.example.postBlog.repository.CommentRepository;



@Service
public class CommentService {
    
    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    

    @Transactional
    public void addComment(CommentEntity newComment){
        commentRepository.save(newComment);
        
    }
   
    public CommentEntity checkComment(Long id){
        Optional<CommentEntity> checkComment = commentRepository.findById(id);

        if(checkComment.isEmpty()){
            throw new CommentDoesNotExistException();
        }
        return checkComment.get();
    }
    @Transactional
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
    @Transactional
    public void deleteListCommentByIdPost(long id){
        List<CommentEntity> list = commentRepository.findByPost(id);
        commentRepository.deleteAll(list);
    }
    @Transactional
    public void deleteListCommentByIdUser(long id){
        List<CommentEntity> list = commentRepository.findByUser(id);
        commentRepository.deleteAll(list);
    }
   
    
}
