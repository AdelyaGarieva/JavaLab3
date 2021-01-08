package spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String _id;
    private String firstName;
    private String lastName;
    private String login;
    private UserRole role;
    private String password;
}
