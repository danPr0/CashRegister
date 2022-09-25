package util;

public class Validator {
    static public boolean validateUsername(String username) {
        return !(username == null || username.isBlank() || username.length() < 3 || username.length() > 16);
    }

    static public boolean validatePassword(String username) {
        return !(username == null || username.isBlank() || username.length() < 8 || username.length() > 24);
    }

    static public boolean validateFirstName(String firstName) {
        return !(firstName == null || firstName.isBlank() || firstName.length() > 30);
    }

    static public boolean validateSecondName(String secondName) {
        return !(secondName == null || secondName.isBlank() || secondName.length() > 30);
    }

    static public boolean validateProductName(String productName) {
        return !(productName == null || productName.isBlank() || productName.length() < 2 || productName.length() > 30);
    }
    
    static public boolean isInteger(String number) {
        boolean result = true;
        try {
            Integer.parseInt(number);
        }
        catch (NumberFormatException e) {
            result = false;
        }
        
        return result;
    }

    static public boolean isDouble(String number) {
        boolean result = true;
        try {
            Double.parseDouble(number);
        }
        catch (NullPointerException | NumberFormatException e) {
            result = false;
        }

        return result;
    }
}
