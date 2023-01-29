package com.bicheka.POJO;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Category category;

    private String description;

    @DateTimeFormat
    @CreatedDate
    private Date dateAdded;

    @NotBlank
    @NotEmpty
    @NotNull
    private String storeId;
}
