package com.webmall.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreStatistics {
    private long amountOfProductsSold;
    private double totalRevenue;
    private long AmountOfProductsOnCart;
    private long AmountOfWishedProducts;

    public StoreStatistics(){
        this.amountOfProductsSold = 0;
        this.totalRevenue = 0.0;
        this.AmountOfProductsOnCart = 0;
        this.AmountOfWishedProducts = 0;
    }
}
