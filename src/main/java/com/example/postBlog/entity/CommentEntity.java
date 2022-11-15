package com.example.postBlog.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
public class CommentEntity {

   @Id
   @GeneratedValue(strategy = (GenerationType.IDENTITY)) 
   private Long id;
   
   @Column(name = "content")
   private String content; 

   @ManyToOne
   @JoinColumn(name = "user_id")
   private UserEntity user; 

   @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "post_id")
   private PostEntity post; 

   @Column(name = "reated_at")
   private Date created;
   
   @Column(name = "updated_at")
   private Date updated; 
    
}
