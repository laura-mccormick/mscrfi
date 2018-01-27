package controller;

import email.EmailSetup;
import objects.RFIDAO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@Controller
@EnableAutoConfiguration
public class FrontController extends HttpServlet {

    private static RFIDAO dao = new RFIDAO();

    private static EmailSetup email = new EmailSetup();

    @RequestMapping("sent/")
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String to, subject, messageContent, alertId;

        to = request.getParameter("sender1");
        alertId = request.getParameter("sender2");
        subject = request.getParameter("subject");
        messageContent = request.getParameter("messageBody");

        try {
            dao.sentMessageToDB(email.sendEmail(to, subject, messageContent), alertId);
        } catch(MessagingException e){
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
        }
        // Commented out until we can grab the current alertId.
//        try {
//            email.sendEmail(to, subject, messageContent, null);
//        } catch (MessagingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }



        pw.printf("<p> Alert: ID" + alertId + "</p><p>Email Sent! </p><p>Sending to: " + to + "</p><p>Sending from: " + " jaketoan@hotmail.co.uk" + "</p><p>Subject: "+ subject + "</p><p>MessageContent: " +messageContent+"</p>");
        pw.printf("<form action='http://localhost:8080/'> <input type='submit' value='Back' /></form>");


        pw.close();
    }
}