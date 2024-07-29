package com.webmall.s3;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3ServiceImpl implements S3Service{

    
    private final S3Client s3;


    public S3ServiceImpl(S3Client s3) { //constructor injection to connect to the s3
        this.s3 = s3;
    }
    
    @Override
    public void putObject(String bucketName, String key, byte[] file){ //store files to a specific bucket with the given key
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(objectRequest, RequestBody.fromBytes(file));
    }

    @Override
    public byte[] getObjectBytes(String bucketName, String key) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

            ResponseInputStream<GetObjectResponse> objectBytes = s3.getObject(objectRequest);
            return objectBytes.readAllBytes();
            
            // ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
            
            // byte[] data = objectBytes.asByteArray();
        
            // return data;

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while downloading file from S3 bucket !!" + e);
        }
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        s3.deleteObject(builder -> builder.bucket(bucketName).key(key));
    }
    
    
    
}
