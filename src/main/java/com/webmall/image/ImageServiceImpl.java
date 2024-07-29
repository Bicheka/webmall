package com.webmall.image;


import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webmall.exeption.EntityNotFoundException;
import com.webmall.product.Product;
import com.webmall.s3.S3Buckets;
import com.webmall.s3.S3Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{

    private S3Service s3Service;
    private MongoTemplate mongoTemplate;
    private S3Buckets s3Buckets;

    
    @Override
    public void deleteProductImage(String productId, String imageId, String userEmail) {
       Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else if(!product.getOwnerEmail().equals(userEmail)){
            throw new UnsupportedOperationException("Only the owner of the product can change its image");
        }
        else{
            try{
                s3Service.deleteObject(
                    s3Buckets.getTest(), 
                    "product-images/%s/%s".formatted(productId, imageId)
                );

                //update the product with id -> "productId" and pull an imageId from its property "imageIds"
                mongoTemplate.update(Product.class)
                    .matching(Criteria.where("id").is(productId))
                    .apply(new org.springframework.data.mongodb.core.query.Update().pull("imageIds", imageId))
                    .first();
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void deleteAllProductImages(String productId, String userEmail) {
        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else if(!product.getOwnerEmail().equals(userEmail)){
            throw new UnsupportedOperationException("Only the owner of the product can change its image");
        }
        else{
            try{
                List<String> productImageIds = product.getImageIds();
                for(String productImageId : productImageIds){
                    s3Service.deleteObject(
                        s3Buckets.getTest(), 
                        "product-images/%s/%s".formatted(productId, productImageId)
                    );
                }
                //update the product with id -> "productId" and set its property "imageIds" to an empty list
                mongoTemplate.update(Product.class)
                    .matching(Criteria.where("id").is(productId))
                    .apply(new org.springframework.data.mongodb.core.query.Update().set("imageIds", new java.util.ArrayList<>()))
                    .first();
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    void checkMaximumNumberOfImages(String productId){
        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

       if(product != null && product.getImageIds().size() >= 6){
           throw new UnsupportedOperationException("Maximum number of images reached");
       }
    }

    // Return type Image

    @Override
    public Image uploadImage(String productId, MultipartFile file, String userEmail) {
        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);
        String productImageId = UUID.randomUUID().toString();

        Image image = new Image();

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
            
        byte[] imageReturned = s3Service.getObjectBytes(
            s3Buckets.getTest(), 
            "product-images/%s/%s".formatted(productId, productImageId)
        );
        
        image.setId(productImageId);
        image.setImage(Base64.getEncoder().encodeToString(imageReturned));

        return image;
        
        }
        else{
            if(product == null){
                throw new EntityNotFoundException(productId, Product.class);
            }
            else if(!product.getOwnerEmail().equals(userEmail)){
                throw new UnsupportedOperationException("Only the owner of the product can upload an image");
            }
        }
        return image;
    }

    @Override
    public Image getImage(String productId, String imageId) {
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

        return new Image(imageId, Base64.getEncoder().encodeToString(image)); //encode the image to base64 string
    }

    @Override
    public Image getImage(String productId) {
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
            return new Image(productImageId, Base64.getEncoder().encodeToString(image)); //encode the image to base64 string
        }
    }

    @Override
    public List<Image> getImages(String productId) {
        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else{
            
            List<String> productImageIds = product.getImageIds();
            List<Image> images = new java.util.ArrayList<>(); //initialize an empty list of byte arrays

            //get all the images of the product with id -> "productId" from s3 bucket and add them to the list of byte arrays "images"
            for(String productImageId : productImageIds){
                
                byte[] image = s3Service.getObjectBytes(
                    s3Buckets.getTest(), 
                    "product-images/%s/%s".formatted(productId, productImageId)
                );

                images.add(new Image(
                    productImageId, 
                    Base64.getEncoder().encodeToString(image)
                ));

            }
            return images;
        }        
    }

    @Override
    public Image changeImage(String productId, String imageId, MultipartFile file, String userEmail) {
        Query productQuery = Query.query(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(productQuery, Product.class);

        if(product == null){
            throw new EntityNotFoundException(productId, Product.class);
        }
        else if(!product.getOwnerEmail().equals(userEmail)){
            throw new UnsupportedOperationException("Only the owner of the product can change its image");
        }
        else{
            try {
                //delete the old image from s3 bucket
                s3Service.deleteObject(
                    s3Buckets.getTest(), 
                    "product-images/%s/%s".formatted(productId, imageId)
                );
                //upload the new image to s3 bucket
                s3Service.putObject(
                    s3Buckets.getTest(), 
                    "product-images/%s/%s".formatted(productId, imageId), 
                    file.getBytes()
                );
                byte[] image = s3Service.getObjectBytes(
                s3Buckets.getTest(), 
                "product-images/%s/%s".formatted(productId, imageId)
                );
                return new Image(imageId, Base64.getEncoder().encodeToString(image));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
