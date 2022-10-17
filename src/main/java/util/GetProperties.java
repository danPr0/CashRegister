package util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * Utility for getting properties from "messages" depending on language
 */
public class GetProperties {
    static public String getMessageByLang(String key, String lang) throws IOException {
        String filename = "messages_" + lang + ".properties";

        Properties properties = new Properties();
        properties.load(new InputStreamReader(Objects.requireNonNull(GetProperties.class.getClassLoader().getResourceAsStream(filename)), StandardCharsets.UTF_8));

        return properties.getProperty(key);
    }
}
