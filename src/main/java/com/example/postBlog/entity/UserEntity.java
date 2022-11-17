package com.example.postBlog.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "users")
public class UserEntity {

   @Id
   @GeneratedValue(strategy = (GenerationType.IDENTITY))
   private Long id;

   @Column(name = "name")
   private String name;

   @Column(name = "email")
   private String email;

   @JsonIgnore
   @Column(name = "password")
   private String password;

   @Column(name = "role")
   private String role;

   @Column(name = "created_at")
   private Date created;

   @Column(name = "updated_at")
   private Date updated;

}
