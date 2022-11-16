package com.example.postBlog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.postBlog.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long>  {
  
    @Query("select p from PostEntity p join p.user u where u.id  = :id")
   List<PostEntity> findAllByUser(Long id);

   @Query("select p from PostEntity p left join p.listComment c  where p.id = :id")
   Optional<PostEntity> findPostAndAllComments( Long id);
}
