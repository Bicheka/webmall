package com.bicheka.service;

import java.util.List;

import com.bicheka.POJO.Comment;

public interface CommentService {
    String createComment(String email, Comment comment);
    String updateComment(String commentId, String text, String email);
    List<Comment> getComments(String productId);
    String deleteComment(String id, String email);
}
