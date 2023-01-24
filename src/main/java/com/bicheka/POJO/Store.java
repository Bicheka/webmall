package com.bicheka.POJO;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String storeName;

    String userEmail;

}
