package com.bicheka.POJO;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
public class Comment {
    @Id
    private String id;

    private String commentText;
    //TODO: it could have images too

    private LocalDateTime createdAt;

    private String createdBy; //name of the user that creates the comment

    private String productId;
}

