package com.example.postBlog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.postBlog.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
   
    @Query("select u from UserEntity u where u.email = :email ")
    Optional<UserEntity> checkUserDatabase(String email);

    
    

}
