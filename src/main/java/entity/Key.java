package entity;

import lombok.*;

/**
 * Entity for "user_key" table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Key {
    private int userId;
    private String key;
}
