package com.singha.journalApp.repository;


import com.singha.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName (String username);

    void deleteByUsername(String username);

}
