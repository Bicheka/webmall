package com.bicheka.comment;

import java.util.List;

public interface CommentService {
    Comment createComment(String email, Comment comment);
    String updateComment(String commentId, String text, String email);
    List<Comment> getComments(String productId);
    String deleteComment(String id, String email);
}
