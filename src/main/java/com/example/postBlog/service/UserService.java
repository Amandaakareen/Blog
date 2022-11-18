package com.example.postBlog.service;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.example.postBlog.entity.UserEntity;
import com.example.postBlog.error.DeniedException;
import com.example.postBlog.error.EntityAlreadyExistException;
import com.example.postBlog.error.EntityDoesNotExistException;
import com.example.postBlog.repository.UserRepository;

@Service
public class UserService {
    
    UserRepository userRepository;
    JwtService jwtService;
    MD5Service md5Service;


    public UserService(UserRepository userRepository, JwtService jwtService, MD5Service md5Service) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.md5Service = md5Service;
    }

    public UserEntity findUserByIdService(Long id){
        Optional<UserEntity> checkUserDatabaseById = userRepository.findById(id);

        if(checkUserDatabaseById.isEmpty()){
        throw new EntityDoesNotExistException();
        }
       
        return checkUserDatabaseById.get();
    }
    
    public List<UserEntity> listUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public void checkUser(UserEntity newUser){
        String md5 = md5Service.generateMD5(newUser.getPassword());

        Optional<UserEntity> checkUserDatabase = userRepository.checkUserDatabase(newUser.getEmail());
        
        if (checkUserDatabase.isPresent()){
            throw new EntityAlreadyExistException();
        }
       newUser.setPassword(md5);
       userRepository.save(newUser);
    
    }
    @Transactional
    public UserEntity editUser(UserEntity userEntity){
        String md5 = md5Service.generateMD5(userEntity.getPassword());
        userEntity.setPassword(md5);
        return userRepository.save(userEntity);
    }

    public String loginUser(String email, String password){
        String md5 = md5Service.generateMD5(password);

        Optional<UserEntity> userDatabase = userRepository.checkUserDatabase(email);

        if(userDatabase.isEmpty()){
            throw new EntityDoesNotExistException();
        }

        UserEntity user = userDatabase.get();

        if(!md5.equals(user.getPassword())){
            throw new  DeniedException();
        }

        String jwt = jwtService.tokenJwt(user.getId());
        return jwt;
    } 
    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    
    } 
}