package com.webmall.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.webmall.product.Product;
import com.webmall.store.Store;
@Service
public class SearchServiceImpl implements SearchService{
    private MongoTemplate mongoTemplate;
    @Override
    public List<Object> searchAllByName(String object_name) {
        // Create a regex for fault-tolerant, case-insensitive search
        String regexPattern = ".*" + object_name + ".*";

        // Create a query object
        Query query = new Query();
        
        // Add criteria to search using regex
        query.addCriteria(Criteria.where("yourField").regex(regexPattern, "i"));

        // Execute the query using MongoTemplatedmgilp123

        List<Product> productObjects = mongoTemplate.find(query, Product.class);
        List<Store> storeObjects = mongoTemplate.find(query, Store.class);

        List<Object> mixedList = new ArrayList<>();

        // Add all products to the mixed list
        mixedList.addAll(productObjects);

        // Add all stores to the mixed list
        mixedList.addAll(storeObjects);

        return mixedList;
    }
    
}
