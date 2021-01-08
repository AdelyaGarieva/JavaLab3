package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import spring.models.User;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SimpleMongoConfig.class);
        MongoTemplate template = context.getBean(MongoTemplate.class);

        User user = User.builder()
                .firstName("Raul")
                .lastName("Gariev")
                .login("rulya")
                .password("rulya")
                .build();
        template.save(user, "user");

        Query query = new Query(Criteria.where("login").is("rulya"));
        Update update = new Update().set("login", "raul");
        template.upsert(query, update, "user");

        query = new Query(Criteria.where("login").is("raul"));
        template.remove(query, "user");
    }
}
