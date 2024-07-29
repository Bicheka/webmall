package com.webmall.product;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import com.webmall.comment.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    @NotBlank
    @NotEmpty
    @NotNull
    private String name;

    @NotBlank
    @NotEmpty
    @NotNull
    private double price;
    
    @NotBlank
    @NotEmpty
    @NotNull
    private ProductCategory category;

    private String description;

    @DateTimeFormat
    @CreatedDate
    private Date dateAdded;

    @NotBlank
    @NotEmpty
    @NotNull
    private String storeId;

    @NotBlank
    @NotEmpty
    @NotNull
    private String ownerEmail;

    @DocumentReference
    List<Comment> comments;

    List<String> imageIds; //store the ids of the images in the s3 bucket
}
