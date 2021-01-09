package ru.itis.dsl.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.dsl.models.Order;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
}
