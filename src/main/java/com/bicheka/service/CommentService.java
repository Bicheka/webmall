package com.bicheka.service;

import java.util.List;

import com.bicheka.POJO.Comment;

public interface CommentService {
    String createComment(String productId, String userEmail, String text);
    String updateComment(String productId, String userEmail, String text);
    List<Comment> getComments(String productId);
    String deleteComment(String productId, String userEmail);
}
