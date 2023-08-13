package com.bicheka.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    //return type Image
    Image uploadImage(String productId, MultipartFile file, String userEmail);
    Image getImage(String productId, String imageId);
    Image getImage(String productId);
    List<Image> getImages(String productId);
    Image changeImage(String productId, String imageId, MultipartFile file, String userEmail);

    void deleteProductImage(String productId, String imageId, String userEmail);

    void deleteAllProductImages(String productId, String userEmail);
}
