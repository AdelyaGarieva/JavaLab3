package models;

import lombok.Data;

@Data
public class UserInfo {
    private String name;
    private String surname;
    private String passportNumber;
    private String age;
    private String issueDate;
}
