package ru.itis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.demo.dto.UserDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthday;

    public static User toDomain(UserDTO userDTO) {
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .middleName(userDTO.getMiddleName())
                .birthday(userDTO.getBirthday())
                .build();
    }
}
