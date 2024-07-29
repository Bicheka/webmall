package com.webmall.image;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<Image> uploadProductImage(@PathVariable String productId, @RequestParam("file") MultipartFile file, Principal principal) {
        return new ResponseEntity<>(imageService.uploadImage(productId, file, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{productId}/get_product_image/{imageId}")
    public ResponseEntity<Image> getProductImage(@PathVariable String productId, @PathVariable String imageId) {
        
        return new ResponseEntity<>(imageService.getImage(productId, imageId), HttpStatus.OK);
    }

    @GetMapping("/{productId}/get_product_image")
    public ResponseEntity<Image> getProductImage(@PathVariable String productId) {
        
        return new ResponseEntity<>(imageService.getImage(productId), HttpStatus.OK);
    }

    //gets product image from s3 bucket
    @GetMapping("/{productId}/get_product_images")
    public ResponseEntity<List<Image>> getImages(@PathVariable String productId) {
        
        return new ResponseEntity<>(imageService.getImages(productId), HttpStatus.OK);
    }

    @PatchMapping("/{productId}/change_product_image/{imageId}")
    public ResponseEntity<Image> changeProductImage(@PathVariable String productId, @PathVariable String imageId, @RequestParam("file") MultipartFile file, Principal principal) {
        return new ResponseEntity<>(imageService.changeImage(productId, imageId, file, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete_product_image/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable String productId, @PathVariable String imageId, Principal principal) {
        imageService.deleteProductImage(productId, imageId, principal.getName());
        return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete_all_product_images")
    public ResponseEntity<String> deleteAllProductImages(@PathVariable String productId, Principal principal) {
        imageService.deleteAllProductImages(productId, principal.getName());
        return new ResponseEntity<>("All images deleted successfully", HttpStatus.OK);
    }
}
