package ru.itis.dsl.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.itis.dsl.models.User;

public interface UsersRepository extends MongoRepository<User, ObjectId>, QuerydslPredicateExecutor<User> {

}



