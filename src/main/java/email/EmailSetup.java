package email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
public class EmailSetup {

    private String username = (System.getProperty("user.name")+"imceu.eu.ssmb.com");

    @Autowired
    Environment resources;

    public Message sendEmail(String to, String subject, String messageContent)
            throws MessagingException {
        Message message = new MimeMessage(this.getSmtpSessionForSendingMessages());

        //message.setFrom(new InternetAddress("jt65062@imceu.eu.ssmb.com"));
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(messageContent);

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        Multipart multipart = new MimeMultipart();

        messageBodyPart = new MimeBodyPart();
        String file = "path of file to be attached";
        String fileName = "attachmentName";
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);

        return message;

    }


    public Session getSmtpSessionForSendingMessages(){
        Properties props = new Properties();
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", resources.getProperty("mail.password"));

        props.put("mail.smtp.host", resources.getProperty("mail.host"));
        props.put("mail.smtp.port", resources.getProperty("mail.access.port"));

        props.put("mail.smtp.starttls.enable", resources.getProperty("mail.starttls.enable"));
        props.put("mail.smtp.auth", resources.getProperty("mail.auth"));

        Authenticator auth = new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(resources.getProperty("mail.username"),resources.getProperty("mail.password"));
            }
        };

        return Session.getInstance(props, auth);

    }

}