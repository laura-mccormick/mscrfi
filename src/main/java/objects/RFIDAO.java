package objects;

import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;

public class RFIDAO {

    private static JdbcTemplate jdbc = new JdbcTemplate();

    public void sentMessageToDB(Message message, String alertId) throws MessagingException, SQLException, IOException {

        RFIDTO rfi = new RFIDTO();

        rfi.setRecipient(message.getAllRecipients()[0].toString());
        rfi.setSender(message.getFrom()[0].toString());
        rfi.setSubject(message.getSubject());
        rfi.setBody(message.getContent().toString());
        rfi.setAlertId(alertId);

        if (returnRfiList(rfi.getAlertId()).isEmpty()) {
            rfi.setMessageId("MSG" + 1 + "RFI" + rfi.getAlertId());
        } else {
            int messageNo = (returnRfiList(rfi.getAlertId()).size() + 1);
            rfi.setMessageId("MSG" + messageNo + "RFI" + rfi.getAlertId());
        }

//		if (returnRfiListById(rfi.getalertId()).isEmpty()) {
//
//			rfi.setMessageId("1");
//
//		} else {
//
//			rfi.setMessageId(returnRfiListById(rfi.getalertId()).get(0).getMessageId() + 1);
//		}


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Dissertation", "TOANDEAF",
                "Waffle12");
        Statement st = connection.createStatement();
        String date = "01/23/2018";
        String sql = String.format("INSERT INTO demo.rfi (messageId, alertId, subject, sender, recipient, body, timestamp) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s');", rfi.getMessageId(), rfi.getAlertId(), rfi.getSubject(), rfi.getSender(), rfi.getRecipient(), rfi.getBody(), date);
        st.executeLargeUpdate(sql);
//		st.executeUpdate(sql);

    }


    public List<RFIDTO> returnRfiList(String alertId) throws SQLException {

        List<RFIDTO> rfiList = new ArrayList<RFIDTO>();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Dissertation", "TOANDEAF",
                "Waffle12");
        Statement st = connection.createStatement();
        String sql = String.format("SELECT * FROM demo.rfi WHERE alertId = %s ORDER BY alertId DESC", alertId);
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {

            rfiList.add(new RFIDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

        }

        connection.close();
        return rfiList;

    }


    public List<RFIDTO> returnFullRFIList() throws SQLException {

        List<RFIDTO> rfiList = new ArrayList<RFIDTO>();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Dissertation", "TOANDEAF",
                "Waffle12");
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM demo.rfi");

        while (rs.next()) {

            rfiList.add(new RFIDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

        }

        connection.close();
        return rfiList;

    }


    public List<RFIDTO> returnFillListDummy() throws JsonProcessingException {

        List<RFIDTO> rfiList = new ArrayList<RFIDTO>();

        RFIDTO rfi1 = new RFIDTO();
        RFIDTO rfi2 = new RFIDTO();
        RFIDTO rfi3 = new RFIDTO();
        RFIDTO rfi4 = new RFIDTO();

        rfi1.setAlertId("1");
        rfi2.setAlertId("1");
        rfi3.setAlertId("1");
        rfi4.setAlertId("2");


        rfi1.setBody("Test body 1");
        rfi2.setBody("Test body 2");
        rfi3.setBody("Test body 3");
        rfi4.setBody("Test body 4");

        rfi1.setMessageId("MSG1RFI1");
        rfi2.setMessageId("MSG2RFI1");
        rfi3.setMessageId("MSG3RFI1");
        rfi4.setMessageId("MSG1RFI2");

        rfi1.setRecipient("test@email.com");
        rfi2.setRecipient("test@email.com");
        rfi3.setRecipient("test@email.com");
        rfi4.setRecipient("test@email.com");

        rfi1.setSender("sender@email.com");
        rfi2.setSender("sender@email.com");
        rfi3.setSender("sender@email.com");
        rfi4.setSender("sender@email.com");

        rfi1.setSubject("Subject 1");
        rfi2.setSubject("Subject 2");
        rfi3.setSubject("Subject 3");
        rfi4.setSubject("Subject 4");

        rfi1.setTimestamp("01/24/2018");
        rfi2.setTimestamp("01/24/2018");
        rfi3.setTimestamp("01/24/2018");
        rfi4.setTimestamp("01/24/2018");


        rfiList.add(rfi1);
        rfiList.add(rfi2);
        rfiList.add(rfi3);
        rfiList.add(rfi4);

        return rfiList;
    }


    public List<RFIDTO> returnFullListDummyById(String alertId) throws JsonProcessingException {

        List<RFIDTO> rfiList = returnFillListDummy();
        List<RFIDTO> filteredList = new ArrayList<>();

        for (int loop = 0; loop < rfiList.size(); loop++) {

            if (rfiList.get(loop).getAlertId() == alertId) {
                filteredList.add(rfiList.get(loop));
            }
        }

        return filteredList;
    }
}



