package entity;

public class Key {
    private int user_id;
    private String key;

    public Key(int user_id, String key) {
        this.user_id = user_id;
        this.key = key;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
