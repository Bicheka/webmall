package com.bicheka.image;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    
    ImageService imageService;

    //uploads product image to s3 bucket
    @PostMapping(value = "/{productId}/upload_product_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProductImage(@PathVariable String productId, @RequestParam("file") MultipartFile file, Principal principal) {
        imageService.uploadProductImage(productId, file, principal.getName());
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }

    //gets product image from s3 bucket
    @GetMapping("/{productId}/get_product_image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable String productId) {
        
        return new ResponseEntity<>(imageService.getProductImage(productId), HttpStatus.OK);
    }

}
