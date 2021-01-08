package spring_jpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User {
    private String _id;
    private String firstName;
    private String lastName;
    private String login;
    private UserRole role;
    private String password;
}
