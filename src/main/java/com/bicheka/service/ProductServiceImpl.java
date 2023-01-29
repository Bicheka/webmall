package com.bicheka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.Product;
import com.bicheka.POJO.Store;
import com.bicheka.exeption.EntityNotFoundException;
import com.bicheka.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private MongoTemplate mongoTemplate;
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        productRepository.insert(product);
        mongoTemplate.update(Store.class)
            .matching(Criteria.where("id").is(product.getStoreId()))
            .apply(new Update().push("products", product))
            .first();
        
        return product;
    }

    @Override
    public Product getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return unwrapProduct(product, id);
    }

    @Override
    public List<Product> getAllProducts() {
        // TODO: Limit the amout of data retrieved;
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        String storeId = mongoTemplate.findOne(query, Product.class).getId();
        mongoTemplate.remove(query, Product.class);

        
        // TODO: Needs optimization adding a Document reference on the entity to its parent to save this query
        Query storeQuery = Query.query(Criteria.where("id").is(storeId));
        Store store = mongoTemplate.findOne(storeQuery, Store.class);
        mongoTemplate.save(store);//update store
    }

    // @Override
    // public void updateProduct(String id, Product updatedProduct) {
    //     mongoTemplate.update(Product.class)
    //         .matching(Criteria.where("id").is(id))
    //         .apply(new Update().for)
    //         .first();
    // }

    static Product unwrapProduct(Optional<Product> entity, String id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Product.class);
    }
    
}
