package spring_jpa.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import spring_jpa.models.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{$or: [{age:{$exists: true}}, {login: ?0}]}")
    List<User> find(@Param("login") String login);

    User findByLogin(String login);
}



