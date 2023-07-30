package com.bicheka.image;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bicheka.exeption.EntityNotFoundException;
import com.bicheka.product.Product;
import com.bicheka.s3.S3Buckets;
import com.bicheka.s3.S3Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{

    private S3Service s3Service;
    private MongoTemplate mongoTemplate;
    private S3Buckets s3Buckets;

    @Override
    public void uploadProductImage(String productId, MultipartFile file, String userEmail) {
        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);
      
        String productImageId = UUID.randomUUID().toString();

        //only the owner of the product can upload an image
        if(product != null && product.getOwnerEmail().equals(userEmail)){
            try {
                s3Service.putObject(
                    s3Buckets.getTest(), 
                    "product-images/%s/%s".formatted(productId, productImageId), 
                    file.getBytes()
                );
                
                //update the product with id -> "productId" and push an imageId into its property "imageIds"
                mongoTemplate.update(Product.class)
                    .matching(Criteria.where("id").is(productId))
                    .apply(new org.springframework.data.mongodb.core.query.Update().push("imageIds", productImageId))
                    .first();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //TODO": store image to database
        }
        else{
            if(product == null){
                throw new EntityNotFoundException(productId, Product.class);
            }
            else if(!product.getOwnerEmail().equals(userEmail)){
                throw new UnsupportedOperationException("Only the owner of the product can upload an image");
            }
        }
    }

    @Override
    public byte[] getProductImage(String productId) {

        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

        // List<byte[]> images = Arrays.asList();

        // byte[] image = null;

        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else{
            return s3Service.getObjectBytes(
                s3Buckets.getTest(), 
                "product-images/%s/%s".formatted(productId, product.getImageIds().get(0))
            );
        }
    }
}
