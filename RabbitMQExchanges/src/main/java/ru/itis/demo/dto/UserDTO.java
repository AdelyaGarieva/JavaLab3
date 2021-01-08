package ru.itis.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthday;
}
