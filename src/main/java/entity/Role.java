package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.RoleName;

@AllArgsConstructor
@Getter @Setter
public class Role {
    private int id;
    private RoleName name;
}
