package com.bicheka.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bicheka.POJO.User;
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	@Query(value = "{'email' : ?0}", delete = true)
    User deleteByEmail(String email);

	@Query(value = "{'username' : ?0}", delete = true)
    User deleteByUsername(String username);

}