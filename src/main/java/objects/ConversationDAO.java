package objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConversationDAO {


    private static JdbcTemplate jdbc = new JdbcTemplate();


    public ConversationDTO createConversationObject(String alertId, String subject, String recipient) throws MessagingException, SQLException, IOException {

        ConversationDTO conversation = new ConversationDTO();

        conversation.setAlertId(alertId);
        conversation.setDateOpened("01/26/2018");
        conversation.setDateClosed("N/A");
        conversation.setSubject(subject);
        conversation.setSender((System.getProperty("user.name") + "@imceu.eu.ssmb.com"));
        conversation.setRecipient(recipient);
        conversation.setOpen(true);
        conversation.setConversationId(ConversationIdSetter(conversation));

        pushConversationObjectToDB(conversation);

        return conversation;
    }

    public String ConversationIdSetter(ConversationDTO conversation) throws SQLException {

        List<ConversationDTO> conversationList = returnConversationListByAlertIDDB(conversation.getAlertId());
        boolean newConversation;

        if (conversationList.isEmpty()) {
            conversation.setConversationId(conversation.getAlertId() + "CONV" + 1);


        } else if(conversationList.size() > 0) {

            conversation.setConversationId(conversation.getAlertId() + "CONV" + (conversationList.size() + 1));

            for(int loop = 0; loop < conversationList.size(); loop++){
                if(conversationList.get(loop).isOpen() == true){
                    conversation.setConversationId(conversationList.get(loop).getConversationId());
                }
            }
        }

        return conversation.getConversationId();
    }


    public void pushConversationObjectToDB(ConversationDTO conversation) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        String sql = String.format("INSERT INTO rfiTool.conversations (alertId, conversationId, subject, sender, recipient, open, dateOpened, dateClosed) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", conversation.getAlertId(), conversation.getConversationId(), conversation.getSubject(), conversation.getSender(), conversation.getRecipient(), 1, conversation.getDateOpened(), conversation.getDateClosed());
        st.executeUpdate(sql);
    }


    public List<ConversationDTO> returnConversationListByAlertIDDB(String alertId) throws SQLException {

        List<ConversationDTO> conversationList = new ArrayList<ConversationDTO>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        String sql = String.format("SELECT * FROM rfiTool.conversations WHERE alertId = '%s' ORDER BY conversationId DESC", alertId);
        ResultSet rs = st.executeQuery(sql);


        while (rs.next()) {

            boolean open = true;

            if (rs.getString(6) == "1") {
                open = true;
            } else if(rs.getString(6) == "0") {
                open = false;
            }


            conversationList.add(new ConversationDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), open, rs.getString(7), rs.getString(8)));

        }

        st.close();
        return conversationList;

    }


    public List<ConversationDTO> returnConversationListFullDB() throws SQLException {

        List<ConversationDTO> conversationList = new ArrayList<ConversationDTO>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM rfiTool.conversations");

        while (rs.next()) {

            boolean open = true;

            if (rs.getString(6) == "1") {
                open = true;
            } else if(rs.getString(6) == "0") {
                open = false;
            }


            conversationList.add(new ConversationDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), open, rs.getString(7), rs.getString(8)));

        }

        st.close();
        return conversationList;

    }


    public List<ConversationDTO> returnConversationListFullDummy() throws JsonProcessingException {

        List<ConversationDTO> conversationList = new ArrayList<ConversationDTO>();

        ConversationDTO conversation1 = new ConversationDTO();
        ConversationDTO conversation2 = new ConversationDTO();
        ConversationDTO conversation3 = new ConversationDTO();
        ConversationDTO conversation4 = new ConversationDTO();

        conversation1.setAlertId("1");
        conversation2.setAlertId("1");
        conversation3.setAlertId("2");
        conversation4.setAlertId("2");

        conversation1.setConversationId("ALERT1CONV1");
        conversation2.setConversationId("ALERT1CONV2");
        conversation3.setConversationId("ALERT2CONV1");
        conversation4.setConversationId("ALERT2CONV2");

        conversation1.setRecipient("test@email.com");
        conversation2.setRecipient("test@email.com");
        conversation3.setRecipient("test@email.com");
        conversation4.setRecipient("test@email.com");

        conversation1.setSender("sender@email.com");
        conversation2.setSender("sender@email.com");
        conversation3.setSender("sender@email.com");
        conversation4.setSender("sender@email.com");

        conversation1.setSubject("Conversation 1");
        conversation2.setSubject("Conversation 2");
        conversation3.setSubject("Conversation 1");
        conversation4.setSubject("Conversation 2");

        conversation1.setOpen(true);
        conversation2.setOpen(false);
        conversation3.setOpen(true);
        conversation4.setOpen(false);

        conversation1.setDateOpened("01/24/2018");
        conversation2.setDateOpened("01/24/2018");
        conversation3.setDateOpened("01/24/2018");
        conversation4.setDateOpened("01/24/2018");

        conversation1.setDateClosed("OPEN");
        conversation2.setDateClosed("01/26/2018");
        conversation3.setDateClosed("OPEN");
        conversation4.setDateClosed("01/25/2018");

        conversationList.add(conversation1);
        conversationList.add(conversation2);
        conversationList.add(conversation3);
        conversationList.add(conversation4);

        return conversationList;
    }


    public List<ConversationDTO> returnConversationListByIdDummy(String alertId) throws JsonProcessingException {

        List<ConversationDTO> conversationList = returnConversationListFullDummy();
        List<ConversationDTO> filteredList = new ArrayList<>();

        for (int loop = 0; loop < conversationList.size(); loop++) {

            if (conversationList.get(loop).getAlertId() == alertId) {
                filteredList.add(conversationList.get(loop));
            }
        }

        return filteredList;
    }

    public ConversationDTO returnConversationObjectByConversationIdDummy(String conversationId) throws JsonProcessingException {

        List<ConversationDTO> conversationList = returnConversationListFullDummy();
        ConversationDTO convo = new ConversationDTO();

        for (int loop = 0; loop < conversationList.size(); loop++) {

            if (conversationList.get(loop).getConversationId() == conversationId) {
                convo = conversationList.get(loop);
            }
        }

        return convo;
    }

    public ConversationDTO returnConversationObjectByConversationId(String conversationId) throws JsonProcessingException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        String sql = String.format("SELECT * FROM rfiTool.conversations WHERE conversationId = %s", conversationId);
        ResultSet rs = st.executeQuery(sql);

        ConversationDTO conversation = new ConversationDTO();

        while (rs.next()) {

            boolean open = true;

            if (rs.getString(6) == "1") {
                open = true;
            } else if (rs.getString(6) == "0") {
                open = false;
            }


            conversation = new ConversationDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), open, rs.getString(7), rs.getString(8));

        }

        st.close();
        return conversation;
    }


    public void updateConversationIsOpen(String conversationId, boolean setOpen) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();

        int rfiBoolean;

        if(setOpen){
            rfiBoolean = 1;
        } else {
            rfiBoolean = 0;
        }

        String sql = String.format("UPDATE rfiTool.conversations SET (isOpen = '%d') WHERE conversationId = '%s';", rfiBoolean, conversationId);

        st.executeQuery(sql);

        st.close();

    }

    public void updateConversationIsOpenDummy(String conversationId, boolean setOpen) throws SQLException, JsonProcessingException {

        for(int loop = 0; loop < returnConversationListFullDummy().size(); loop++){
            if(returnConversationListFullDummy().get(loop).getConversationId() == conversationId){
                returnConversationListFullDummy().get(loop).setOpen(setOpen);
            }
        }
    }



}



