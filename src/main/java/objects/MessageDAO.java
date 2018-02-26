package objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAO {



    private static JdbcTemplate jdbc = new JdbcTemplate();



    public MessageDTO createMessageObject(String to, String subject, String messageContent, String conversationId) throws MessagingException, SQLException, IOException {

        MessageDTO messageDto = new MessageDTO();

        messageDto.setRecipient(to);
        messageDto.setSender((System.getProperty("user.name") + "imceu.eu.ssmb.com"));
        messageDto.setSubject(subject);
        messageDto.setBody(messageContent);
        messageDto.setConversationId(conversationId);
        messageDto.setMessageId(messageIdSetter(messageDto));

        pushMessageObjectToDB(messageDto);

        return messageDto;
    }


    public static String messageIdSetter(MessageDTO messageDto) throws SQLException {

        List<MessageDTO> messageList = returnMessageListByIdDB(messageDto.getConversationId());

        if (messageList.isEmpty()) {
            messageDto.setMessageId(messageDto.getConversationId() + "MSG" + 1);

        } else {
            messageDto.setMessageId(messageDto.getConversationId() + "MSG" + (messageList.size() + 1));
        }

        return messageDto.getMessageId();
    }

    public void pushMessageObjectToDB(MessageDTO message) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        String date = "01/24/2018";
        String sql = String.format("INSERT INTO rfiTool.messages (messageId, conversationId, subject, sender, recipient, body, timestamp) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s');", message.getMessageId(), message.getConversationId(), message.getSubject(), message.getSender(), message.getRecipient(), message.getBody(), date);
        st.executeUpdate(sql);
        st.close();
    }





    public static List<MessageDTO> returnMessageListByIdDB(String conversationId) throws SQLException {

        List<MessageDTO> messageList = new ArrayList<MessageDTO>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        String sql = String.format("SELECT * FROM rfiTool.messages WHERE conversationId = '%s' ORDER BY conversationId DESC", conversationId);
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {

            messageList.add(new MessageDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

        }

        st.close();
        return messageList;

    }





    public List<MessageDTO> returnMessageListFullDummy() throws JsonProcessingException {

        List<MessageDTO> messageList = new ArrayList<MessageDTO>();

        MessageDTO message1 = new MessageDTO();
        MessageDTO message2 = new MessageDTO();
        MessageDTO message3 = new MessageDTO();
        MessageDTO message4 = new MessageDTO();
        MessageDTO message5 = new MessageDTO();

        message1.setConversationId("ALERT1CONV1");
        message2.setConversationId("ALERT1CONV1");
        message3.setConversationId("ALERT1CONV2");
        message4.setConversationId("ALERT2CONV1");
        message5.setConversationId("ALERT2CONV2");

        message1.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin bibendum molestie augue, eu elementum ipsum commodo ut. Nullam tristique posuere pharetra. Integer blandit dolor non massa dignissim "
        		+ "tincidunt. Cras convallis ante ornare dolor vestibulum, vel viverra tortor vulputate. Nam vehicula ornare pellentesque. Vestibulum venenatis sagittis felis nec mollis. Integer dictum varius sem placerat"
        		+ " fermentum. Pellentesque imperdiet massa in lacinia vehicula. Donec at aliquam neque. Ut congue, mi vitae sagittis pulvinar, velit augue cursus urna, nec posuere purus ante id augue. Donec sapien lectus,"
        		+ " euismod sed massa at, imperdiet feugiat nunc. Proin dapibus dictum nisi sed finibus. Morbi pellentesque cursus ullamcorper. Praesent ullamcorper non massa id tincidunt. Etiam auctor, sapien nec tristique"
        		+ " porta, nisl nunc rhoncus nibh, maximus hendrerit quam dui sed libero. Aliquam egestas vulputate sapien tincidunt tristique. Aliquam nec nunc ut turpis efficitur gravida ut quis sem. Maecenas ac sapien "
        		+ "sed dolor porta maximus ut id leo. Suspendisse potenti. Sed a diam aliquet, interdum risus id, fermentum lectus. Nullam elit lorem, malesuada a elementum nec, laoreet eget nunc. Aliquam pharetra purus id "
        		+ "urna pretium, at congue sapien hendrerit. Morbi rhoncus consectetur aliquam. Vivamus vel mauris dictum, tincidunt urna cursus, euismod magna. Vestibulum pulvinar hendrerit augue, ut rutrum erat rhoncus in."
        		+ " Donec aliquet enim lacus, a dapibus tortor volutpat luctus. Nulla massa quam, accumsan quis ligula ut, auctor tincidunt justo."+
        		"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin bibendum molestie augue, eu elementum ipsum commodo ut. Nullam tristique posuere pharetra. Integer blandit dolor non massa dignissim "
        		+ "tincidunt. Cras convallis ante ornare dolor vestibulum, vel viverra tortor vulputate. Nam vehicula ornare pellentesque. Vestibulum venenatis sagittis felis nec mollis. Integer dictum varius sem placerat"
        		+ " fermentum. Pellentesque imperdiet massa in lacinia vehicula. Donec at aliquam neque. Ut congue, mi vitae sagittis pulvinar, velit augue cursus urna, nec posuere purus ante id augue. Donec sapien lectus,"
        		+ " euismod sed massa at, imperdiet feugiat nunc. Proin dapibus dictum nisi sed finibus. Morbi pellentesque cursus ullamcorper. Praesent ullamcorper non massa id tincidunt. Etiam auctor, sapien nec tristique"
        		+ " porta, nisl nunc rhoncus nibh, maximus hendrerit quam dui sed libero. Aliquam egestas vulputate sapien tincidunt tristique. Aliquam nec nunc ut turpis efficitur gravida ut quis sem. Maecenas ac sapien "
        		+ "sed dolor porta maximus ut id leo. Suspendisse potenti. Sed a diam aliquet, interdum risus id, fermentum lectus. Nullam elit lorem, malesuada a elementum nec, laoreet eget nunc. Aliquam pharetra purus id "
        		+ "urna pretium, at congue sapien hendrerit. Morbi rhoncus consectetur aliquam. Vivamus vel mauris dictum, tincidunt urna cursus, euismod magna. Vestibulum pulvinar hendrerit augue, ut rutrum erat rhoncus in."
        		+ " Donec aliquet enim lacus, a dapibus tortor volutpat luctus. Nulla massa quam, accumsan quis ligula ut, auctor tincidunt justo.");
        message2.setBody("Test body 2");
        message3.setBody("Test body 3");
        message4.setBody("Test body 4");
        message5.setBody("Test body 5");

        message1.setMessageId("MSG1ALERT1RFI1");
        message2.setMessageId("MSG2ALERT1RFI1");
        message3.setMessageId("MSG1ALERT1RFI2");
        message4.setMessageId("MSG1ALERT2RFI1");
        message5.setMessageId("MSG1ALERT2RFI2");

        message1.setRecipient("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin bibendum molestie augue, eu elementum ipsum commodo ut. Nullam tristique posuere pharetra. Integer blandit dolor non massa dignissim ");
        message2.setRecipient("test@email.com");
        message3.setRecipient("test@email.com");
        message4.setRecipient("test@email.com");
        message5.setRecipient("test@email.com");

        message1.setSender("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin bibendum molestie augue, eu elementum ipsum commodo ut. Nullam tristique posuere pharetra. Integer blandit dolor non massa dignissim ");
        message2.setSender("sender@email.com");
        message3.setSender("sender@email.com");
        message4.setSender("sender@email.com");
        message5.setSender("sender@email.com");

        message1.setSubject("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin bibendum molestie augue, eu elementum ipsum commodo ut. Nullam tristique posuere pharetra. Integer blandit dolor non massa dignissim ");
        message2.setSubject("Subject 2");
        message3.setSubject("Subject 3");
        message4.setSubject("Subject 4");
        message5.setSubject("Subject 5");

        message1.setTimestamp("01/24/2018 00:00:00.00");
        message2.setTimestamp("01/24/2018 00:00:00.00");
        message3.setTimestamp("01/24/2018 00:00:00.00");
        message4.setTimestamp("01/24/2018 00:00:00.00");
        message5.setTimestamp("01/24/2018 00:00:00.00");

        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);
        messageList.add(message4);
        messageList.add(message5);

        return messageList;
    }


    public List<MessageDTO> returnMessageListFullDB() throws SQLException {

        List<MessageDTO> messageList = new ArrayList<MessageDTO>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM rfiTool.messages");

        while (rs.next()) {

            messageList.add(new MessageDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

        }

        st.close();
        return messageList;

    }


    public List<MessageDTO> returnMessageListByIdDummy(String conversation) throws JsonProcessingException {


        List<MessageDTO> messageList = returnMessageListFullDummy();
        List<MessageDTO> filteredList = new ArrayList<>();

        for (int loop = 0; loop < messageList.size(); loop++) {

            if (messageList.get(loop).getConversationId() == conversation) {
                filteredList.add(messageList.get(loop));
            }
        }

        return filteredList;
    }

    public MessageDTO returnMessageByMessageIdDUMMY(String messageId, String conversationId) throws JsonProcessingException {

        List<MessageDTO> messageList = returnMessageListByIdDummy(conversationId);
        MessageDTO selectedMessage = null;

        for(int loop = 0; loop < messageList.size(); loop++ ){
            if(messageList.get(loop).getMessageId() == messageId){
                selectedMessage = messageList.get(loop);
            }
        }

       return selectedMessage;
    }


    public MessageDTO returnMessageByMessageIdDB(String messageId, String conversationId) throws SQLException {

        List<MessageDTO> messageList = returnMessageListByIdDB(conversationId);
        MessageDTO selectedMessage = null;

        for(int loop = 0; loop < messageList.size(); loop++ ){
            if(messageList.get(loop).getMessageId() == messageId){
                selectedMessage = messageList.get(loop);
            }
        }





        return selectedMessage;
    }
}
