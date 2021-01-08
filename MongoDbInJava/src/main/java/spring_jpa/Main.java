package spring_jpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_jpa.models.User;
import spring_jpa.repositories.UserRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RepositoriesConfig.class);
        UserRepository usersRepository = context.getBean(UserRepository.class);

        User user = User.builder()
                .firstName("Raul")
                .lastName("Gariev")
                .login("rulya")
                .password("rulya")
                .build();
        usersRepository.save(user);

        List<User> users = usersRepository.find("rulya");
        User user1 = users.get(0);
        user1.setLogin("raul");
        usersRepository.save(user1);

        user = usersRepository.findByLogin("raul");
        usersRepository.delete(user);
    }
}
