package com.example.postBlog.service;


import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        CommentEntity commentEntity = newCommentTest();

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
        CommentEntity commentEntity = newCommentTest();
        Mockito.when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));

        //ação
        commentService.checkComment(commentEntity.getId());
        //varificação
        Mockito.verify(commentRepository).findById(commentEntity.getId());

    }

    @Test
    void testDeleteComment() {
        //cenario
        CommentEntity commentEntity = newCommentTest();
        
        //ação
        commentService.deleteComment(commentEntity.getId());

        //varificação
        Mockito.verify(commentRepository).deleteById(commentEntity.getId());
      


    }

    @Test
    void testDeleteListCommentByIdPost() {
        //cenario
        CommentEntity commentEntity = newCommentTest();
        List<CommentEntity> list = listCommentTest();

        Mockito.when(commentRepository.findByPost(commentEntity.getPost().getId())).thenReturn(list);
        
        //ação
        commentService.deleteListCommentByIdPost(commentEntity.getPost().getId());
        //varificação
        Mockito.verify(commentRepository, times(1)).findByPost(commentEntity.getPost().getId());
        Mockito.verify(commentRepository).deleteAll(list);

    }

    @Test
    void testDeleteListCommentByIdUser() {
        CommentEntity commentEntity = newCommentTest();
        List<CommentEntity> list = listCommentTest();

        Mockito.when(commentRepository.findByUser(commentEntity.getUser().getId())).thenReturn(list);
        commentService.deleteListCommentByIdUser(commentEntity.getUser().getId());
        //varificação
        Mockito.verify(commentRepository).findByUser(commentEntity.getUser().getId());
        Mockito.verify(commentRepository).deleteAll(list);

    }

    private CommentEntity newCommentTest(){
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
        
        postEntity.setId(10L);
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

        return commentEntity;

    }

    private List<CommentEntity> listCommentTest(){
        

        Date data= new Date();
        CommentEntity commentEntity = new CommentEntity();
        CommentEntity commentEntity2 = new CommentEntity();
        UserEntity userEntity =new UserEntity();
        PostEntity postEntity = new PostEntity();
        

        userEntity.setId(1L);
        userEntity.setName("amanda");
        userEntity.setEmail("mandakaren526292@gmail.com");
        userEntity.setPassword("123456");
        userEntity.setCreated(data);
        userEntity.setUpdated(data);
        userEntity.setRole("user");
        
        postEntity.setId(10L);
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

        commentEntity2.setId(1L);
        commentEntity2.setPost(postEntity);
        commentEntity2.setUser(userEntity);
        commentEntity2.setContent("bla");
        commentEntity2.setCreated(data);
        commentEntity2.setUpdated(data);
        List<CommentEntity> list = new ArrayList<CommentEntity>();
        list.add(commentEntity);
        list.add(commentEntity2);

        
        return list;

    }
}
