package Util;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;

public class EmailSender {


    public static void sendEmailWithReport(String reportPath,
                                           String smtpHost,
                                           String smtpPort,
                                           String senderEmail,
                                           String senderPassword,
                                           String recipientEmail) {
        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.host", smtpHost);
//        props.put("mail.smtp.port", smtpPort);



        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Add debugging to see SMTP conversation
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            message.setSubject("Test Execution Report");

            // Create text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Please find the test report attached.");

            // Create attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(reportPath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("Test_Report.html");

            // Create multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            // Print full stack trace for debugging
            e.printStackTrace();

            // More detailed error handling
            if (e instanceof MessagingException) {
                MessagingException me = (MessagingException) e;
                System.err.println("Messaging Exception Details:");
                me.printStackTrace();
                if (me.getNextException() != null) {
                    System.err.println("Nested Exception:");
                    me.getNextException().printStackTrace();
                }
            }
        }
    }
}
