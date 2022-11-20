package com.example.postBlog.service;


import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.postBlog.entity.CommentEntity;
import com.example.postBlog.entity.PostEntity;
import com.example.postBlog.entity.UserEntity;
import com.example.postBlog.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock
    CommentRepository commentRepository;
    @Captor
    ArgumentCaptor<CommentEntity> commentEntityCaptor;
   
    

    @Test
    void testAddComment() {
        //cenario

        Date data= new Date();
        CommentEntity commentEntity = new CommentEntity();
        UserEntity userEntity =new UserEntity();
        PostEntity postEntity = new PostEntity();

        userEntity.setId(1L);
        userEntity.setName("amanda");
        userEntity.setEmail("mandakaren526292@gmail.com");
        userEntity.setPassword("123456");
        userEntity.setCreated(data);
        userEntity.setUpdated(data);
        userEntity.setRole("user");
        
        postEntity.setTitle("amanda");
        postEntity.setContent("conteudo");
        postEntity.setUser(userEntity);
        postEntity.setCreated(data);
        postEntity.setUpdated(data);

        commentEntity.setId(1L);
        commentEntity.setPost(postEntity);
        commentEntity.setUser(userEntity);
        commentEntity.setContent("bla");
        commentEntity.setCreated(data);
        commentEntity.setUpdated(data);

        //Mockito.when(commentRepository.save(commentEntity)).thenReturn(commentEntity);

        //ação
        
        commentService.addComment(commentEntity);

        //varificação
        Mockito.verify(commentRepository).save(commentEntityCaptor.capture());
        CommentEntity comment = commentEntityCaptor.getValue();

        //testando possibilidades
        Assertions.assertThat(comment.getCreated()).isNotNull();
        
       
     }

   
    @Test
    void testCheckComment(){
        //cenario
        
        Date data= new Date();
        CommentEntity commentEntity = new CommentEntity();
        UserEntity userEntity =new UserEntity();
        PostEntity postEntity = new PostEntity();

        userEntity.setId(1L);
        userEntity.setName("amanda");
        userEntity.setEmail("mandakaren526292@gmail.com");
        userEntity.setPassword("123456");
        userEntity.setCreated(data);
        userEntity.setUpdated(data);
        userEntity.setRole("user");
        
        postEntity.setTitle("amanda");
        postEntity.setContent("conteudo");
        postEntity.setUser(userEntity);
        postEntity.setCreated(data);
        postEntity.setUpdated(data);

        commentEntity.setId(1L);
        commentEntity.setPost(postEntity);
        commentEntity.setUser(userEntity);
        commentEntity.setContent("bla");
        commentEntity.setCreated(data);
        commentEntity.setUpdated(data);

        Mockito.when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));

        //ação
        commentService.checkComment(commentEntity.getId());
        //varificação
        Mockito.verify(commentRepository).findById(commentEntity.getId());

    }

    @Test
    void testDeleteComment() {
        //cenario

        //ação

        //varificação


    }

    @Test
    void testDeleteListCommentByIdPost() {
        //cenario

        //ação

        //varificação


    }

    @Test
    void testDeleteListCommentByIdUser() {
        //cenario

        //ação

        //varificação


    }
}
