package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private Role role;
}
