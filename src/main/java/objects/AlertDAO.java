package objects;
import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableAutoConfiguration
public class AlertDAO {

    public List<AlertDTO> returnFullAlertListDB() throws SQLException {

        List<AlertDTO> alertList = new ArrayList<>();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM rfiTool.alerts");

        while (rs.next()) {

            boolean hasRfi;

            if(rs.getInt(3) == 1){
                hasRfi = true;
            } else {
                hasRfi = false;
            }

            alertList.add(new AlertDTO(rs.getString(1), rs.getString(2), hasRfi));

        }

        st.close();
        return alertList;


    }

    public List<AlertDTO> returnFullAlertListDummy() throws JsonProcessingException {


        List<AlertDTO> dummyList = new ArrayList<AlertDTO>();

        AlertDTO alert1 = new AlertDTO();
        AlertDTO alert2 = new AlertDTO();
        AlertDTO alert3 = new AlertDTO();
        AlertDTO alert4 = new AlertDTO();
        AlertDTO alert5 = new AlertDTO();
        AlertDTO alert6 = new AlertDTO();
        AlertDTO alert7 = new AlertDTO();
        AlertDTO alert8 = new AlertDTO();

        alert1.setId("1");
        alert2.setId("2");
        alert3.setId("3");
        alert4.setId("4");
        alert5.setId("5");
        alert6.setId("6");
        alert7.setId("7");
        alert8.setId("8");

        alert1.setDesc("Big cheeky");
        alert2.setDesc("Big small");
        alert3.setDesc("Big dad");
        alert4.setDesc("Mad bad");
        alert5.setDesc("Big Cheeky");
        alert6.setDesc("Wingle bad");
        alert7.setDesc("Big France");
        alert8.setDesc("It is bad");

        alert1.setHasRfi(true);
        alert2.setHasRfi(false);
        alert3.setHasRfi(true);
        alert4.setHasRfi(false);
        alert5.setHasRfi(false);
        alert6.setHasRfi(true);
        alert7.setHasRfi(false);
        alert8.setHasRfi(true);

        dummyList.add(alert1);
        dummyList.add(alert2);
        dummyList.add(alert3);
        dummyList.add(alert4);
        dummyList.add(alert5);
        dummyList.add(alert6);
        dummyList.add(alert7);
        dummyList.add(alert8);

        return dummyList;
    }

    public AlertDTO returnAlertById(String alertId) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();
        String sql = String.format("SELECT * FROM rfiTool.alerts WHERE alertId = %s", alertId);
        ResultSet rs = st.executeQuery(sql);

        boolean hasRfi;

        if(rs.getInt(3) == 1){
            hasRfi = true;
        } else {
            hasRfi = false;
        }

        AlertDTO alertDto = new AlertDTO(rs.getString(1), rs.getString(2), hasRfi);

        st.close();
        return alertDto;
    }

    public void updateAlertHasRFIvalue(String alertId, boolean hasRfi) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        Statement st = connection.createStatement();

        int rfiBoolean;

        if(hasRfi){
            rfiBoolean = 1;
        } else {
            rfiBoolean = 0;
        }

        String sql = String.format("UPDATE rfiTool.alerts SET (hasRfi = '%d') WHERE alertId = '%s';", rfiBoolean, alertId);

        st.executeQuery(sql);

        st.close();

    }

}