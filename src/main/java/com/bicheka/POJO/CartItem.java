package com.bicheka.POJO;

import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    @DocumentReference
    private Product product;

    private Integer quantity;
}
