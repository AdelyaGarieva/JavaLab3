package hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User {
    @Id
    private String _id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
