package com.prabhash.megacity.servlet;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;

public class EmailService {

    private final String smtpHost = "smtp.gmail.com"; // SMTP Server
    private final String smtpPort = "587"; // Port (587 for TLS, 465 for SSL)
    private final String username = "textilelana@gmail.com"; // Your email
    private final String password = "tota nyxu wphu egxo"; // Your email password

    public void sendTestEmail(String recipientEmail, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Create session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a new MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Create the HTML email content
            String htmlContent = "<html><body>"
                    + "<h1 style='color: #2c3e50;font-size: 24px;'>Welcome to MegaCity!</h1>"
                    +" "
                    + "<p style='font-size: 16px;'> Thank you for joining our amazing community. We’re thrilled to have you on board! </p>"
                    + "<img src='cid:megaCityImage' alt='MegaCity Logo' />" // Reference the embedded image here
                    + "</body></html>";

            // Create MimeMultipart object for handling attachments and HTML content
            MimeMultipart multipart = new MimeMultipart("related");

            // Create the HTML part of the email
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html");

            // Add HTML part to the multipart
            multipart.addBodyPart(htmlPart);

            // Attach the image as a CID (Content-ID)
            MimeBodyPart imagePart = new MimeBodyPart();
            DataSource fds = new FileDataSource("E:\\MegaCity\\MegaCity\\src\\main\\resources\\email\\Black and Orange Car Rent Logo.png"); // Update with the actual path to your image
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<megaCityImage>");

            // Add image part to the multipart
            multipart.addBodyPart(imagePart);

            // Set the content of the email to be the multipart object
            message.setContent(multipart);

            // Send the email
            Transport.send(message); // Send the email

            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
