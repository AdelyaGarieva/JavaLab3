package ru.itis.demo.exchanges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "ru.itis.demo.exchanges",
        "ru.itis.demo.config",
        "ru.itis.demo.controllers",
        "ru.itis.demo.services"
})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
