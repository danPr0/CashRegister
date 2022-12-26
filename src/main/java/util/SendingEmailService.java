package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.enums.Language;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static util.GetProperties.getMessageByLang;

public class SendingEmailService {
    private static Session session = null;
    private final static Logger logger = LogManager.getLogger(SendingEmailService.class);

    static {
        Properties prop = new Properties();
        try {
            Properties appProp = new Properties();
            appProp.load(SendingEmailService.class.getClassLoader().getResourceAsStream("application.properties"));
            prop.put("mail.smtp.host", appProp.getProperty("mail.smtp.host"));
            prop.put("mail.smtp.port", appProp.getProperty("mail.smtp.port"));
            prop.put("mail.smtp.auth", appProp.getProperty("mail.smtp.auth"));
            prop.put("mail.smtp.starttls.enable", appProp.getProperty("mail.smtp.starttls.enable"));
            prop.put("mail.smtp.ssl.trust", appProp.getProperty("mail.smtp.ssl.trust"));
            prop.put("mail.smtp.ssl.protocols", appProp.getProperty("mail.smtp.ssl.protocols"));

            String username = appProp.getProperty("mail.username");
            String password = appProp.getProperty("mail.password");

            session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        }
        catch (IOException e) {
            logger.error("Cannot establish the connection with mail service", e);
        }
    }

    public static void sendResetPasswordEmail(String receiver, String newPassword, String confirmLink, Language lang) throws MessagingException {
        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress("cashregister666@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(getMessageByLang("email.passwordReset.subject", lang));

        String msg = String.format("<p>%s%s</p>" +
                "<p>%s<a href=%s>%s<a/></p>", getMessageByLang("email.passwordReset.p1", lang), newPassword,
                getMessageByLang("email.passwordReset.p2", lang), confirmLink, getMessageByLang("email.passwordReset.link", lang));

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    public static void sendSignupConfirmationEmail(String receiver, String confirmLink, Language lang) throws MessagingException {
        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress("cashregister666@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(getMessageByLang("email.signupConfirmation.subject", lang));

        String msg = String.format("<p>%s<a href=%s>%s<a/></p>", getMessageByLang("email.signupConfirmation.p1", lang),
                confirmLink, getMessageByLang("email.signupConfirmation.link", lang));

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    public static void sendZReport(String receiver, Language lang, String ...filenames) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(getMessageByLang("email.ZReport.subject", lang));

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        for (String filename : filenames)
            attachmentBodyPart.attachFile(new File(filename));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(attachmentBodyPart);

        message.setContent(multipart);
        Transport.send(message);
    }
}
