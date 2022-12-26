package util.enums;

import util.GetProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Roles enum
 */
public enum RoleName {
    cashier,
    commodity_expert,
    senior_cashier,
    guest,
    admin;

    public static List<String> getRoles(Language lang) {
        List<String> result = new ArrayList<>();

        List<RoleName> roles = List.of(values());
        roles.forEach(role -> result.add(GetProperties.getMessageByLang("", lang)));

        return result;
    }
}
