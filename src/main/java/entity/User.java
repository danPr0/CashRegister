package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.enums.RoleName;

/**
 * Entity for "users" table
 */
@AllArgsConstructor
@Getter @Setter
public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private RoleName roleId;
}
