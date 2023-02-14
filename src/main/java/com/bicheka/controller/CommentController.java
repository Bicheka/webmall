package com.bicheka.controller;

import java.security.Principal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bicheka.POJO.Comment;
import com.bicheka.service.CommentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    // String createComment(String email, Comment comment);
    // String updateComment(String commentId, String text, String email);
    // List<Comment> getComments(String productId);
    // String deleteComment(String id, String email);
    CommentService commentService;

    @PostMapping("/create-comment")
    public ResponseEntity<String> createComment(Principal principal, Comment comment){
        return new ResponseEntity<>(commentService.createComment(principal.getName(), comment), HttpStatus.OK);
    }

    @PutMapping("/update-comment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable String commentId, @RequestBody String Text, Principal principal){
        return new ResponseEntity<>(commentService.updateComment(commentId, Text, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/get-comments/{productId}")
    public ResponseEntity<List<Comment>> getComments(String productId){
        return new ResponseEntity<>(commentService.getComments(productId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<String> deleteComment(String id, Principal principal){
        return new ResponseEntity<>(commentService.deleteComment(id, principal.getName()), HttpStatus.OK);
    }
}
