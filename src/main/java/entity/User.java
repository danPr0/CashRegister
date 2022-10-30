package entity;

import lombok.*;
import util.enums.RoleName;


/**
 * Entity for "users" table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private RoleName roleId;
}
