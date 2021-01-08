package ru.itis.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.itis.demo.models.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
}
