package com.example.postBlog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.postBlog.entity.PostEntity;
import com.example.postBlog.error.PostDoesNotExistException;
import com.example.postBlog.repository.PostRepository;




@Service
public class PostService {
    
    PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostEntity> findAllPosts(){
        List<PostEntity>  listPosts = postRepository.findAll();
        return listPosts;
    }

    public void addPost(PostEntity newPost){
        postRepository.save(newPost);  
    }

    public PostEntity findPostById(Long id){
        Optional<PostEntity> post = postRepository.findPostAndAllComments(id);
        if(post.isEmpty()){
            throw new PostDoesNotExistException();
        }
        return post.get();
    }

    public void editPost(PostEntity post){
        postRepository.save(post); 
    }

    public void deletePostById(Long id){
        Optional<PostEntity> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new PostDoesNotExistException();
        } 
        postRepository.deleteById(id);
    }

    public List<PostEntity> findAllPostsByUser(Long id){
        List<PostEntity> postUser = postRepository.findAllByUser(id);

        return postUser;
    }

    public void listDeletePostByUser(Long id){
        List<PostEntity> postUser = postRepository.findAllByUser(id);
        postRepository.deleteAll(postUser);
    }
}
