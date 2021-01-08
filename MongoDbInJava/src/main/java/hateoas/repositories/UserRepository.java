package hateoas.repositories;

import hateoas.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{$or: [{age:{$exists: true}}, {login: ?0}]}")
    List<User> find(@Param("login") String login);

    @RestResource(path = "bLogin", rel = "byLogin")
    User findByLogin(String login);
}



