package service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static javax.mail.Session.getInstance;

public class SendOTPService {
    public static void sendOTP(String email, String genOTP) {
        String to = email;
        String from = "agampalsingh25@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "dghnqasgvpkzzbhn"); // No spaces here
            }
        });


        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your One-Time Pin for File Encryption");
            message.setText("Thank you for using File Encrypter. Your One-Time Password is : " + genOTP);
            System.out.println("Sending...");
            Transport.send(message);
            System.out.println("Message Sent Successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
