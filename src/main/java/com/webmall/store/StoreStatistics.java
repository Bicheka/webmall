package com.webmall.store;

import java.util.List;

import com.webmall.product.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreStatistics {
    private long amountOfProductsSold;
    private double totalRevenue;
    private long AmountOfProductsOnCart;
    private long AmountOfWishedProducts;
    private List<Product> topFiveProducts;

    public StoreStatistics(){
        this.amountOfProductsSold = 0;
        this.totalRevenue = 0.0;
        this.AmountOfProductsOnCart = 0;
        this.AmountOfWishedProducts = 0;
    }
}
