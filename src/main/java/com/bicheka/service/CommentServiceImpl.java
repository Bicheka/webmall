package com.bicheka.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.Comment;
import com.bicheka.repository.CommentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    CommentRepository commentRepository;
    MongoTemplate mongoTemplate;

    @Override
    public String createComment(String email, Comment comment) {
        comment.setCreatedBy(email);
        mongoTemplate.save(comment);
        return "comment created";
    }

    @Override
    public String updateComment(String commentId, String text, String email) {
        Query query = Query.query(Criteria.where("id").is(commentId));
        Comment comment = mongoTemplate.findOne(query, Comment.class);
        if(comment == null){
            return "comment not found";
        }
        if(comment.getCreatedBy().equals(email)){ //check if the comment belongs to the user
            LocalDateTime currentDateTime = LocalDateTime.now();
            comment.setLastUpdated(currentDateTime);
            comment.setCommentText(text);
            mongoTemplate.save(comment);
            return "comment updated";
        }
        else{
            return "error occurred";
        }
    }

    @Override
    public List<Comment> getComments(String productId) {
        Query query = Query.query(Criteria.where("productId").is(productId));
        return mongoTemplate.find(query, Comment.class);
    }

    @Override
    public String deleteComment(String commentId, String email) throws RuntimeException{
        CriteriaDefinition criteriaDefinition = new Criteria().andOperator(
            Criteria.where("id").is(commentId),
            Criteria.where("createdBy").is(email)
        );
        try{
            mongoTemplate.remove(Query.query(criteriaDefinition), Comment.class);
            return "comment deleted";
        }
        catch(RuntimeException e){
            return "comment could not be deleted because it was not found or it is not yours";
        }

        
    }

    
    
}
