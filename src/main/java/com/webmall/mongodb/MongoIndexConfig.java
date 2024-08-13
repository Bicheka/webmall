package com.webmall.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;

import com.webmall.product.Product;
import com.webmall.store.Store;

import jakarta.annotation.PostConstruct;

@Configuration
public class MongoIndexConfig {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoIndexConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void initIndexes() {
        // Create text index on the Product collection
        mongoTemplate.indexOps(Product.class).ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name")
                .onField("description")
                .build());

        // Create text index on the Store collection
        mongoTemplate.indexOps(Store.class).ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name")
                .build());
    }
}
