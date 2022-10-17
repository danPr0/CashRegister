package entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity for "user_key" table
 */
@AllArgsConstructor
@Getter @Setter
public class Key {
    private int user_id;
    private String key;
}
