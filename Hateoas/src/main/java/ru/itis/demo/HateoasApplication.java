package ru.itis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.itis.demo.models.*;
import ru.itis.demo.repositories.GoodRepository;
import ru.itis.demo.repositories.OrderRepository;
import ru.itis.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HateoasApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HateoasApplication.class, args);

        GoodRepository goodRepository = context.getBean(GoodRepository.class);
        Good good1 = goodRepository.save(Good.builder()
                .name("Колонка")
                .price(18990.00)
                .build());
        Good good2 =goodRepository.save(Good.builder()
                .name("Ноутбук")
                .price(97000.00)
                .build());
        Good good3 = goodRepository.save(Good.builder()
                .name("Кактус")
                .price(750.50)
                .build());

        UserRepository userRepository = context.getBean(UserRepository.class);
        List<Good> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        User user1 = userRepository.save(User.builder()
                .firstName("Adelya")
                .lastName("Garieva")
                .role(Role.ADMIN)
                .build());

        OrderRepository orderRepository = context.getBean(OrderRepository.class);
        orderRepository.save(Order.builder()
                .goods(goods)
                .user(user1)
                .status(Status.NOT_DELIVERED)
                .build());
    }
}
