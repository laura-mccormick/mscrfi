package controller;

import email.EmailSetup;
import objects.*;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@EnableAutoConfiguration
public class EmailController extends HttpServlet {

    private static ConversationDAO conversationDAO = new ConversationDAO();
    private static MessageDAO messageDao = new MessageDAO();
    private static AttachmentDAO attachmentDAO = new AttachmentDAO();
    private static EmailSetup email = new EmailSetup();

    @RequestMapping("sent/")
    protected void httpServletGrabber(HttpServletRequest request,
                                   HttpServletResponse response) throws ServletException, IOException, MessagingException, SQLException {

        response.setContentType("text/html");

        String to, subject, messageContent, conversationId;
        Part pdfFilePart;

        to = request.getParameter("sender1");
        conversationId = request.getParameter("sender2");
        subject = request.getParameter("subject");
        messageContent = request.getParameter("messageBody");
        pdfFilePart = request.getPart("generatedPDF");
        
        List<Part> parts = request.getParts().stream().filter(part -> "attachments".equals(part.getName())).collect(Collectors.toList());
        parts.add(pdfFilePart);
        
        List<File> files = partsToFilesTransformer(parts);

        email.sendEmail(to, subject, messageContent, files, conversationId);

        initateObjectCreation(to, subject, messageContent, files, conversationId);

        response.sendRedirect("http://localhost:8080/");

    }

    public static List<File> partsToFilesTransformer(List<Part> parts) throws ServletException, IOException, MessagingException, SQLException {

        List<File> files = new ArrayList<>();

        for(Part part : parts){

            File file = new File(part.getSubmittedFileName());
            FileUtils.copyInputStreamToFile(part.getInputStream(), file);
            files.add(file);

        }

        for(File file : files) {

            System.out.println(file.getAbsolutePath());
            System.out.println(file.getName());
            System.out.println(file.length());

        }

        return files;
    }


    public static void initateObjectCreation(String to, String subject, String messageContent, List<File> files, String conversationId) throws IOException, SQLException, MessagingException {

        if (conversationId.contains("ALERT")) {

            String alertId = conversationId.substring(5);

            ConversationDTO conversation = conversationDAO.createConversationObject(alertId, subject, to);

            MessageDTO message = messageDao.createMessageObject(to, subject, messageContent, conversation.getConversationId());

            if (!files.isEmpty()) {
                    attachmentDAO.pushAttachmentToDB(files, message.getMessageId());
            }

        } else {


            ConversationDTO conversation = conversationDAO.returnConversationObjectByConversationId(conversationId);

            MessageDTO message = messageDao.createMessageObject(to, subject, messageContent, conversation.getConversationId());

            if (!files.isEmpty()) {
                    attachmentDAO.pushAttachmentToDB(files, message.getMessageId());
            }


        }

    }
}
