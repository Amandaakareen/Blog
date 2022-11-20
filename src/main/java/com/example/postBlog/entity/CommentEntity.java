package com.example.postBlog.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "comments")
public class CommentEntity {

   @Id
   @GeneratedValue(strategy = (GenerationType.IDENTITY)) 
   private Long id;
   
   @Column(name = "content")
   private String content; 
   
   @JsonIgnore
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

  


   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      CommentEntity other = (CommentEntity) obj;
      if (content == null) {
         if (other.content != null)
            return false;
      } else if (!content.equals(other.content))
         return false;
      return true;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((content == null) ? 0 : content.hashCode());
      return result;
   }

   
   
    
}
