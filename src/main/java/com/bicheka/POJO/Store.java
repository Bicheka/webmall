package com.bicheka.POJO;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("store")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    private String id;

    @NotBlank(message =  "name cannot be blank")
    @NonNull
    private String storename;

}
