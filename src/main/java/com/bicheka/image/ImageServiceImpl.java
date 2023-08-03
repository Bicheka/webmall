package com.bicheka.image;


import java.util.Base64;
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
    public String getProductImage(String productId, String imageId) {

        if(mongoTemplate.findOne(Query.query(Criteria.where("id").is(productId)), Product.class) == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else if(mongoTemplate.findOne(Query.query(Criteria.where("imageIds").is(imageId)), Product.class) == null){
            throw new EntityNotFoundException(imageId, Product.class);
        }

        byte[] image = s3Service.getObjectBytes(
            s3Buckets.getTest(), 
            "product-images/%s/%s".formatted(productId, imageId)
        );

        return Base64.getEncoder().encodeToString(image); //encode the image to base64 string
    }

    @Override
    public String getProductImage(String productId) {

        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }

        String productImageId = product.getImageIds().get(0);

        if(product.getImageIds().equals(null) || product.getImageIds().isEmpty()){
            throw new EntityNotFoundException("Image not found");
        }
        else{
            byte[] image = s3Service.getObjectBytes(
                s3Buckets.getTest(), 
                "product-images/%s/%s".formatted(productId, productImageId)
            );
            return Base64.getEncoder().encodeToString(image); //encode the image to base64 string
        }
    }

    @Override
    public List<byte[]> getProductImages(String productId) {

        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else{
            
            List<String> productImageIds = product.getImageIds();
            List<byte[]> images = new java.util.ArrayList<>(); //initialize an empty list of byte arrays

            //get all the images of the product with id -> "productId" from s3 bucket and add them to the list of byte arrays "images"
            for(String productImageId : productImageIds){
                
                images.add(s3Service.getObjectBytes(
                    s3Buckets.getTest(), 
                    "product-images/%s/%s".formatted(productId, productImageId)
                ));

            }
            return images;
        }
    }
}
