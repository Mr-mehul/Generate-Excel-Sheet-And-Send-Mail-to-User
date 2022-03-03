package com.example.demo.email;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;

@Component
public class AwsSesExample {
	
    @Autowired
    public AmazonSimpleEmailService amazonSimpleEmailService;

    public void sendEmailWithAttachmentFromLocalStorage() {

        Session session = Session.getInstance(new Properties(System.getProperties()));
        MimeMessage mimeMessage = new MimeMessage(session);
        String emailContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Example HTML Email with Attachment</title>\n" +
                "</head>\n" +
                "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n" +
                "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Dear example,</h5>\n" +
                "<p style=\"font-size: 16px; font-weight: 500\">Greetings from TutorialsBuddy</p>\n" +
                "<p>This is a simple html based email with attachment.</p>\n" +
                "</body>\n" +
                "</html>";

         String filePath = "F:\\java-tutorials.pdf";
        String fileName = "java-tutorials.pdf";

        try {
            mimeMessage.setSubject("Test Email", "UTF-8");

            mimeMessage.setFrom("example@tutorialsbuddy.com");
            mimeMessage.setRecipients(RecipientType.TO, "receiver@gmail.com");

            MimeMultipart msgBody = new MimeMultipart("alternative");
            MimeBodyPart wrap = new MimeBodyPart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(emailContent, "text/html; charset=UTF-8");
            msgBody.addBodyPart(htmlPart);
            wrap.setContent(msgBody);

            MimeMultipart msg = new MimeMultipart("mixed");
            mimeMessage.setContent(msg);
            msg.addBodyPart(wrap);

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Attachment pdf file
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            
            msg.addBodyPart(messageBodyPart);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mimeMessage.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
            amazonSimpleEmailService.sendRawEmail(rawEmailRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    

	public void sendEmailWithAttachment(ByteArrayOutputStream outputStreamData) {

        Session session = Session.getInstance(new Properties(System.getProperties()));
        MimeMessage mimeMessage = new MimeMessage(session);
        String emailContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Example HTML Email with Attachment</title>\n" +
                "</head>\n" +
                "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n" +
                "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Dear example,</h5>\n" +
                "<p style=\"font-size: 16px; font-weight: 500\">Greetings from TutorialsBuddy</p>\n" +
                "<p>This is a simple html based email with attachment.</p>\n" +
                "</body>\n" +
                "</html>";


        try {
            mimeMessage.setSubject("Test Email", "UTF-8");

            mimeMessage.setFrom("example@tutorialsbuddy.com");
            mimeMessage.setRecipients(RecipientType.TO, "receiver@gmail.com");

            MimeMultipart msgBody = new MimeMultipart("alternative");
            MimeBodyPart wrap = new MimeBodyPart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(emailContent, "text/html; charset=UTF-8");
            msgBody.addBodyPart(htmlPart);
            wrap.setContent(msgBody);

            MimeMultipart msg = new MimeMultipart("mixed");
            mimeMessage.setContent(msg);
            msg.addBodyPart(wrap);

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Attachment pdf file
            DataSource source = new ByteArrayDataSource(outputStreamData.toByteArray(), "application/vnd.ms-excel");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("vnd.ms-excel");
            
            msg.addBodyPart(messageBodyPart);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mimeMessage.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
            amazonSimpleEmailService.sendRawEmail(rawEmailRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
