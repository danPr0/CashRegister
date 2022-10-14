package util;

public class Validator {
//    public static boolean validateUsername(String username) {
//        return !(username == null || username.matches("([A-Za-zА-Яа-яЁёЇїІіЄєҐґ'0-9_]+)") || username.length() < 3 || username.length() > 16);
//    }

    public static boolean validateEmail(String email) {
        return email != null && email.length() >= 3 && email.length() <= 254;
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 55;
    }

    public static boolean validateFirstName(String firstName) {
        return firstName != null && firstName.matches("[-.\sA-Za-z]+|[-.\sА-Яа-яЁёЇїІіЄєҐґ']+") && firstName.length() <= 48;
    }

    public static boolean validateSecondName(String secondName) {
        return secondName != null && secondName.matches("[-.\sA-Za-z]+|[-.\sА-Яа-яЁёЇїІіЄєҐґ']+") && secondName.length() <= 48;
    }

    public static boolean validateProductName(String productName) {
        return productName != null && productName.length() <= 60;
    }

    public static boolean validateQuantity(ProductMeasure measure, double quantity) {
        return quantity > 0 && quantity < 10_000 && !(measure.equals(ProductMeasure.apiece) && quantity % 1 != 0);
    }

    public static boolean validatePrice(double price) {
        return price > 0 && price < 10_000_000;
    }
}
