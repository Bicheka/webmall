package com.bicheka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bicheka.POJO.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>{
    
}
