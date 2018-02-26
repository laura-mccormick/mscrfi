package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Testttt


{

    private static ConversationDAO conversationDAO = new ConversationDAO();
    private static MessageDAO messageDao = new MessageDAO();
    private static AttachmentDAO attachmentDAO = new AttachmentDAO();


    public static void main(String [] args) throws IOException, SQLException, MessagingException {

        Map<String, String> mapz = new HashMap<String, String>();

        mapz.put("jaketoan@hotmail.co.uk", "Jake Toan");

        List<String> keyz = mapz.keySet().stream().collect(Collectors.toList());

        System.out.println(mapz.get(keyz.get(0)));
        System.out.println(keyz.get(0));
    }
}
