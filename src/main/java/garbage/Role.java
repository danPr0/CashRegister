package garbage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.enums.RoleName;

/**
 * Entity for "roles" table
 */
@AllArgsConstructor
@Getter @Setter
public class Role {
    private int id;
    private RoleName name;
}
