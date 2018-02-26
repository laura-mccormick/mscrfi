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
import java.io.File;
import java.util.List;
import java.util.Properties;

@Component
public class EmailSetup {

    private String username = (System.getProperty("user.name")+"imceu.eu.ssmb.com");

    @Autowired
    Environment resources;

    public Message sendEmail(String to, String subject, String messageContent, List<File> files, String conversationId)
            throws MessagingException {

        Message message = new MimeMessage(this.getSmtpSessionForSendingMessages());

        //message.setFrom(new InternetAddress("jt65062@imceu.eu.ssmb.com"));
        message.setFrom(new InternetAddress("jaketoan@hotmail.co.uk"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        String content = messageContent;
        content += "<div><input type=\"hidden\" name=\"RFINumber\" value=\""+conversationId+"\"></div>";
        message.setText(content);

        Multipart multipart = new MimeMultipart();

        for(File file : files){

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);

        }

        message.setContent(multipart);

        Transport.send(message);

        return message;

    }


    public Session getSmtpSessionForSendingMessages(){

        Properties props = new Properties();

        props.put("mail.smtp.user", "jaketoan@hotmail.co.uk");
        props.put("mail.smtp.password", "Jakeemma12");

        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jaketoan@hotmail.co.uk", "Jakeemma12");
            }
        };

        return Session.getInstance(props, auth);

    }
}