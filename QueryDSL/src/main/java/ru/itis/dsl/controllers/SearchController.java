package ru.itis.dsl.controllers;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dsl.dto.UserDto;
import ru.itis.dsl.models.User;
import ru.itis.dsl.repositories.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class SearchController {
    @Autowired
    private UsersRepository userRepository;

    @GetMapping("/users/search")
    public ResponseEntity<List<UserDto>> searchByPredicate(@QuerydslPredicate(root = User.class) Predicate predicate) {
        StreamSupport.stream(userRepository.findAll(predicate).spliterator(), true).forEach(user -> System.out.println("test -> " + user.getFirstName()));
        return ResponseEntity.ok(
            StreamSupport.stream(userRepository.findAll(predicate).spliterator(), true)
                .map(user -> UserDto.builder()
                        .firstName(user.getFirstName())
                        .login(user.getLogin())
                        .id(user.get_id().toString())
                        .build()).collect(Collectors.toList()));
    }
}
