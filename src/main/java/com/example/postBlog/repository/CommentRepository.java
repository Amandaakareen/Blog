package com.example.postBlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.postBlog.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long >{

    @Query("select c from CommentEntity c join c.post p where p.id = :id")
    List<CommentEntity> findByPost(Long id);
    @Query("select c from CommentEntity c join c.user u where u.id  = :id")
    List<CommentEntity> findByUser(Long id);
}
