package dto;

import entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  DTO for {@link entity.User}
 */
@AllArgsConstructor
@Getter @Setter
public class UserDTO {
    private String email;
    private String firstName;
    private String secondName;
    private String role;
}
