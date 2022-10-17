package util.db;

import static util.db.DBFields.*;
import static util.db.DBFields.CHECK_ID;

public class DBQueryConstants {
    public static final String CHECK_GET_BY_PRODUCT_QUERY = "SELECT * FROM `check` WHERE %s = ?".formatted(CHECK_PRODUCT_ID);
    public static final String CHECK_GET_NUMBER_OF_ROWS = "SELECT COUNT(*) FROM `check`";
    public static final String CHECK_GET_SEGMENT_QUERY = "SELECT * FROM `check` ORDER BY %s %s LIMIT ? OFFSET ?";
    public static final String CHECK_GET_ALL_QUERY = "SELECT * FROM `check`";
    public static final String CHECK_INSERT_QUERY = "INSERT INTO `check` (%s, %s) VALUES (?, ?)"
            .formatted(CHECK_PRODUCT_ID, CHECK_PRODUCT_QUANTITY);
    public static final String CHECK_UPDATE_BY_ID_QUERY = "UPDATE `check` SET %s = ? WHERE %s = ?"
            .formatted(CHECK_PRODUCT_QUANTITY, CHECK_ID);
    public static final String CHECK_DELETE_BY_ID_QUERY = "DELETE FROM `check` WHERE %s = ?".formatted(CHECK_ID);
    public static final String CHECK_DELETE_ALL_QUERY = "DELETE FROM `check`";

    public static final String KEY_INSERT_QUERY = "INSERT INTO user_key (%s, %s) VALUES (?, ?)".formatted(KEY_USER_ID, KEY_KEY);
    public static final String KEY_GET_BY_USER_ID = "SELECT * FROM user_key WHERE %s = ?".formatted(KEY_USER_ID);

    public static final String PRODUCT_GET_BY_NAME_QUERY = "SELECT * FROM products WHERE %s = ?".formatted(PRODUCT_NAME);
    public static final String PRODUCT_GET_BY_ID_QUERY = "SELECT * FROM products WHERE %s = ?".formatted(PRODUCT_ID);
    public static final String PRODUCT_INSERT_QUERY = "INSERT INTO products (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
            .formatted(PRODUCT_NAME, PRODUCT_MEASURE, PRODUCT_QUANTITY, PRODUCT_PRICE);
    public static final String PRODUCT_UPDATE_QUERY = "UPDATE products SET %s = ?, %s = ? WHERE %s = ?"
            .formatted(PRODUCT_QUANTITY, PRODUCT_PRICE, PRODUCT_NAME);

    public static final String REPORT_INSERT_QUERY = "INSERT INTO report (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
            .formatted(REPORT_CREATED_BY, REPORT_CLOSED_AT, REPORT_ITEMS_QUANTITY, REPORT_TOTAL_PRICE);
    public static final String REPORT_GET_NUMBER_OF_ROWS = "SELECT COUNT(*) FROM report";
    public static final String REPORT_GET_SEGMENT_QUERY = "SELECT * FROM report ORDER BY %s %s LIMIT ? OFFSET ?";
    public static final String REPORT_GET_ALL_QUERY = "SELECT * FROM report";
    public static final String REPORT_DELETE_ALL_QUERY = "DELETE FROM report";

    public static final String ROLE_GET_BY_ID_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_ID);
    public static final String ROLE_GET_BY_NAME_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_NAME);
    
    public static final String USER_GET_BY_EMAIL_QUERY = "SELECT * FROM users WHERE %s = ?".formatted(USER_EMAIL);
    public static final String USER_INSERT_QUERY = "INSERT INTO users (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
            .formatted(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID);
    public static final String USER_UPDATE_ROLE_BY_ID = "UPDATE users SET %s = ? WHERE %s = ?".formatted(USER_ROLE_ID, USER_ID);
}
