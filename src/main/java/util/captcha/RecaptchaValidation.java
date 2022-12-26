package util.captcha;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Properties;

public class RecaptchaValidation {
    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || gRecaptchaResponse.isBlank()) {
            return false;
        }
        Properties properties = new Properties();
        properties.load(RecaptchaValidation.class.getClassLoader().getResourceAsStream("application.properties"));

        HttpsURLConnection con = getConnection(properties.getProperty("recaptcha.verificationUrl"),
                properties.getProperty("recaptcha.secret"), gRecaptchaResponse);
        String response = getResponse(con);
        if (response == null)
            return false;

        JsonReader jsonReader = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        return jsonObject.getBoolean("success") && jsonObject.getJsonNumber("score").bigDecimalValue().doubleValue() > 0.5;
    }

    private static HttpsURLConnection getConnection(String url, String secret, String gRecaptchaResponse) {
        HttpsURLConnection result;
        try {
            URL obj = new URL(url);
            result = (HttpsURLConnection) obj.openConnection();
            result.setRequestMethod("POST");
            result.setDoOutput(true);

            String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;
            DataOutputStream wr = new DataOutputStream(result.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    private static String getResponse(HttpsURLConnection con) {
        String result;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder resultBuilder = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                resultBuilder.append(inputLine);
            }
            in.close();

            result = resultBuilder.toString();
        } catch (Exception e) {
            result = null;
        }

        return result;
    }
}
