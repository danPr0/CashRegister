package garbage;

import garbage.Role;

public interface RoleService {
    Role getRole(int id);
    Role getRole(String name);
}
