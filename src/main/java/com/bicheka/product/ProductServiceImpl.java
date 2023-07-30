package com.bicheka.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bicheka.exeption.EntityNotFoundException;
import com.bicheka.store.Store;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private MongoTemplate mongoTemplate;
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product, String ownerEmail) {
        product.setOwnerEmail(ownerEmail);//set the owner of the product
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
    public String deleteProduct(String id, String userEmail) {
        Query query = Query.query(Criteria.where("id").is(id));
        Product product = mongoTemplate.findOne(query, Product.class);

        if(product == null) return "Product not found";

        String storeId = product.getStoreId();

        // TODO: Needs optimization adding a Document reference on the entity to its parent to save this query
        Query storeQuery = Query.query(Criteria.where("id").is(storeId));
        Store store = mongoTemplate.findOne(storeQuery, Store.class);

        if(store == null) return "Store not found";

        if(!store.getUserEmail().equals(userEmail)){
            return "Reuest denied because product with id: "+ id +" is not owned by you";
        }
        mongoTemplate.remove(query, Product.class);
        
        //update store
        mongoTemplate.save(mongoTemplate.findOne(storeQuery, Store.class));

        return "Product deleted";
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
