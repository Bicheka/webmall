package com.bicheka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bicheka.s3.S3Service;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
public class BichekaApplication {

	@Autowired
	S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(BichekaApplication.class, args);
	}

	// @Bean
	// CommandLineRunner runner(S3Service s3Service, S3Buckets s3Buckets) {
	// 	return args -> {
	// 		s3Service.putObject(s3Buckets.getProduct(), "foo", "hello world".getBytes());
	// 		byte[] object = s3Service.getObjectBytes("bicheka-file-storage-test", "foo");

	// 		System.out.println("Yeii!! "+ new String(object));
	// 	};
	// }

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}



}