package com.webmall.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets") //from the properties file we have aws.s3.buckets.test
@Getter
@Setter
public class S3Buckets {
    
    private String test; //this is going to hold the value of aws.s3.buckets.test on the properties file


}
