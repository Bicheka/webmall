package com.webmall.store;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.webmall.product.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("store")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Store{
    
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "The name of the store cannot be blank")
    @NotEmpty
    @NotNull
    private String storeName;

    @DocumentReference
    private List<Product> products;

    //private about;

    private String userEmail;

    //this property must be updated so only the owner of the store can see it
    private StoreStatistics statistics;

}
