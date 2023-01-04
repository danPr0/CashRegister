package util.enums;

import util.GetProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Roles enum
 */
public enum RoleName {
    cashier {
        @Override
        public String getTranslation(String lang) {
            return GetProperties.getMessageByLang("select.userRole.cashier", Language.valueOf(lang));
        }
    },
    commodity_expert {
        @Override
        public String getTranslation(String lang) {
            return GetProperties.getMessageByLang("select.userRole.commodity_expert", Language.valueOf(lang));
        }
    },
    senior_cashier {
        @Override
        public String getTranslation(String lang) {
            return GetProperties.getMessageByLang("select.userRole.senior_cashier", Language.valueOf(lang));
        }
    },
    guest {
        @Override
        public String getTranslation(String lang) {
            return GetProperties.getMessageByLang("select.userRole.guest", Language.valueOf(lang));
        }
    },
    admin {
        @Override
        public String getTranslation(String lang) {
            return GetProperties.getMessageByLang("select.userRole.admin", Language.valueOf(lang));
        }
    };

    public abstract String getTranslation(String lang);

//    public static List<String> getRoles(Language lang) {
//        List<String> result = new ArrayList<>();
//
//        List<RoleName> roles = List.of(values());
//        roles.forEach(role -> result.add(GetProperties.getMessageByLang("select.userRole." + role.name(), lang)));
//
//        return result;
//    }
}
