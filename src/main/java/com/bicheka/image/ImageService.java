package com.bicheka.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    // void uploadImage(String bucketName, String key, byte[] file);

    void uploadProductImage(String productId, MultipartFile file, String userEmail);

    byte[] getProductImage(String productId);
}
