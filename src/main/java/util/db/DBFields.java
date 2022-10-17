package util.db;

/**
 * Utility represents constants of all columns in database
 */
public class DBFields {
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_SECOND_NAME = "second_name";
    public static final String USER_ROLE_ID = "role_id";

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_KEY = "secret_key";

    public static final String ROLE_ID = "id";
    public static final String ROLE_NAME = "name";

    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_MEASURE = "measure";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_PRICE = "price";

    public static final String CHECK_ID = "id";
    public static final String CHECK_PRODUCT_ID = "product_id";
    public static final String CHECK_PRODUCT_QUANTITY = "quantity";

    public static final String REPORT_ID = "id";
    public static final String REPORT_CREATED_BY = "created_by";
    public static final String REPORT_CLOSED_AT = "closed_at";
    public static final String REPORT_ITEMS_QUANTITY = "items_quantity";
    public static final String REPORT_TOTAL_PRICE = "total_price";
}
