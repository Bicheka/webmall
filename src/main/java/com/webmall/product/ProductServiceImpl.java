package com.webmall.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.webmall.exeption.EntityNotFoundException;
import com.webmall.image.ImageService;
import com.webmall.store.Store;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private MongoTemplate mongoTemplate;
    private ProductRepository productRepository;
    private ImageService imageService;

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
        
        //remove all images of the product from aws s3 when deleting the product
        imageService.deleteAllProductImages(id, userEmail);

        Store new_store = mongoTemplate.findOne(storeQuery, Store.class);
        if(new_store ==null){
            throw new EntityNotFoundException();
        }
        //update store
        mongoTemplate.save(new_store);

        return "Product deleted";
    }

    static Product unwrapProduct(Optional<Product> entity, String id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Product.class);
    }

    @Override
    public void updateProductPrice(String id, double price, String userEmail) {
        if(checkRequestValidity(id, userEmail)){
           mongoTemplate.update(Product.class)
            .matching(Criteria.where("id").is(id))
            .apply(new Update().set("price", price))
            .first();
        }
        else{
            throw new UnsupportedOperationException("Only the owner of the product can update the price");
        }

    }

    @Override
    public void updateProductName(String id, String name, String userEmail) {
        if(checkRequestValidity(id, userEmail)){
            mongoTemplate.update(Product.class)
            .matching(Criteria.where("id").is(id))
            .apply(new Update().set("name", name))
            .first();
        }
        else{
            throw new UnsupportedOperationException("Only the owner of the product can update the name");
        }
    }

    @Override
    public void updateProductDescription(String id, String description, String userEmail) {
       if(checkRequestValidity(id, userEmail)){
            mongoTemplate.update(Product.class)
            .matching(Criteria.where("id").is(id))
            .apply(new Update().set("description", description))
            .first();
        }
        else{
            throw new UnsupportedOperationException("Only the owner of the product can update the description");
       }
    } 

    boolean checkIfProductExists(String productId){
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(productId)), Product.class);
    }

    boolean checkIfUserIsOwnerOfProduct(String productId, String userEmail){
        Query query = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(query, Product.class);
        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else if(!product.getOwnerEmail().equals(userEmail)){
            return false;
        }
        return true;
    }

    boolean checkRequestValidity(String productId, String userEmail){
        if(checkIfProductExists(productId) && checkIfUserIsOwnerOfProduct(productId, userEmail)){
            return true;
        }
        else{
            return false;
        }
    }

}
