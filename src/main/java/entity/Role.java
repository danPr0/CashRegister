package entity;

import util.RoleName;

public class Role {
    private int id;
    private RoleName name;

    public Role(int id, String name) {
        this.id = id;
        this.name = RoleName.valueOf(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
