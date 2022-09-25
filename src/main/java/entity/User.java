package entity;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private Role role;

    public User(int id, String username, String password, String firstName, String secondName, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
