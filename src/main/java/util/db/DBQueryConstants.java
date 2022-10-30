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
    public static final String CHECK_DELETE_BY_PRODUCT_ID_QUERY = "DELETE FROM `check` WHERE %s = ?".formatted(CHECK_PRODUCT_ID);
    public static final String CHECK_DELETE_ALL_QUERY = "DELETE FROM `check`";

    public static final String KEY_INSERT_QUERY = "INSERT INTO user_key (%s, %s) VALUES (?, ?)".formatted(KEY_USER_ID, KEY_KEY);
    public static final String KEY_GET_BY_USER_ID = "SELECT * FROM user_key WHERE %s = ?".formatted(KEY_USER_ID);
    public static final String KEY_UPDATE_QUERY = "UPDATE user_key SET %s = ? WHERE %s = ?".formatted(KEY_KEY, KEY_USER_ID);

    public static final String PRODUCTS_GET_BY_NAME_QUERY = "SELECT * FROM products WHERE %s = ?".formatted(PRODUCT_ORIGINAL_NAME);
    public static final String PRODUCTS_GET_BY_ID_QUERY = "SELECT * FROM products WHERE %s = ?".formatted(PRODUCT_ID);
    public static final String PRODUCTS_INSERT_QUERY = "INSERT INTO products (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
            .formatted(PRODUCT_ORIGINAL_NAME, PRODUCT_ORIGINAL_LANG_ID, PRODUCT_MEASURE, PRODUCT_QUANTITY, PRODUCT_PRICE);
    public static final String PRODUCTS_UPDATE_QUERY = "UPDATE products SET %s = ?, %s = ? WHERE %s = ?"
            .formatted(PRODUCT_QUANTITY, PRODUCT_PRICE, PRODUCT_ID);
    public static final String PRODUCTS_DELETE_QUERY = "DELETE FROM products WHERE %s = ?".formatted(PRODUCT_ID);

    public static final String REPORT_INSERT_QUERY = "INSERT INTO report (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
            .formatted(REPORT_USER_ID, REPORT_CLOSED_AT, REPORT_ITEMS_QUANTITY, REPORT_TOTAL_PRICE);
    public static final String REPORT_GET_NUMBER_OF_ROWS = "SELECT COUNT(*) FROM report";
    public static final String REPORT_GET_SEGMENT_QUERY = "SELECT * FROM report ORDER BY %s %s LIMIT ? OFFSET ?";
    public static final String REPORT_GET_ALL_QUERY = "SELECT * FROM report";
    public static final String REPORT_DELETE_ALL_QUERY = "DELETE FROM report";

    public static final String ROLES_GET_BY_ID_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_ID);
    public static final String ROLES_GET_BY_NAME_QUERY = "SELECT * FROM roles WHERE %s = ?".formatted(ROLE_NAME);

    public static final String USERS_GET_BY_ID_QUERY = "SELECT * FROM users WHERE %s = ?".formatted(USER_ID);
    public static final String USERS_GET_BY_EMAIL_QUERY = "SELECT * FROM users WHERE %s = ?".formatted(USER_EMAIL);
    public static final String USERS_INSERT_QUERY = "INSERT INTO users (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
            .formatted(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID);
    public static final String USERS_UPDATE_QUERY = "UPDATE users SET %s = ?, %s = ? WHERE %s = ?".formatted(USER_PASSWORD, USER_ROLE_ID, USER_ID);

//    public static final String LANG_GET_BY_VAR = "SELECT * FROM language WHERE %s = ?".formatted(LANG_VAR);

    public static final String PRODUCTS_TRANSLATIONS_GET_BY_PRODUCT_ID = "SELECT * FROM products_translations WHERE %s = ?"
            .formatted(PRODUCT_TRANSLATION_PRODUCT_ID);
    public static final String PRODUCTS_TRANSLATIONS_GET_BY_PRODUCT_TRANSLATION = "SELECT * FROM products_translations WHERE %s = ? AND %s = ?"
            .formatted(PRODUCT_TRANSLATION_LANG_ID, PRODUCT_TRANSLATION_PRODUCT_TRANSLATION);
    public static final String PRODUCTS_TRANSLATIONS_GET_PRODUCT_NAME = "SELECT %s FROM products_translations WHERE %s = ? AND %s = ?"
            .formatted(PRODUCT_TRANSLATION_PRODUCT_TRANSLATION, PRODUCT_TRANSLATION_PRODUCT_ID, PRODUCT_TRANSLATION_LANG_ID);
    public static final String PRODUCTS_TRANSLATIONS_INSERT = "INSERT INTO products_translations (%s, %s, %s) VALUES (?, ?, ?)"
            .formatted(PRODUCT_TRANSLATION_PRODUCT_ID, PRODUCT_TRANSLATION_LANG_ID, PRODUCT_TRANSLATION_PRODUCT_TRANSLATION);
}
