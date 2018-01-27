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

    /*
     * Constructor, if true is passed in
     * Autoloads both of the list by calling the appropriate methods
     * Otherwise, lists are empty.
     */
    public AlertDAO(boolean loaded)  {
        if (loaded == true) {
            try{

                alertDtoLoad();
                convertAlertListToJson();

            } catch(SQLException sqlExc){
                sqlExc.getStackTrace();
            } catch (JsonProcessingException jsonExc) {
                jsonExc.getStackTrace();
            }

        } else {}



    }

    /*
     * Default constructor
     */
    public AlertDAO(){

    }

    // List to store the actual alertDTO objects
    public static List<AlertDTO> alertList = new ArrayList<AlertDTO>();

    // List to store JSON object versions of the Alert DTOs
    public static List<String> alertListJson = new ArrayList<String>();

    // Configured jdbcTemplate that comes with connection details set
    // Set within the beans.xml, this may need work havent live tested.
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    // Object mapper for JSON conversion.
    private ObjectMapper mapper = new ObjectMapper();


    /**
     * Method that grabs the data from the database
     * If jdbcTemplate configuration doesn't work, initiates connection manually
     * Maps the resultset to new DTO objects, loads the list and sets the list.
     */
    public List<AlertDTO> alertDtoLoad() throws SQLException {

        alertList.clear();


        // Clearly subject to change.
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Dissertation", "TOANDEAF",
                "Waffle12");
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM demo.alert");

        while (rs.next()) {

            boolean hasRfi;

            if(rs.getInt(3) == 1){
                hasRfi = true;
            } else {
                hasRfi = false;
            }

            alertList.add(new AlertDTO(rs.getString(1), rs.getString(2), hasRfi));

        }
        connection.close();
        return alertList;


    }




    /**
     *
     * Method converts alertList to JSON, stores it in alertListJson
     */
    public void convertAlertListToJson() throws JsonProcessingException, SQLException {

        for (int loop = 0; loop < alertList.size(); loop++) {
            alertListJson.add(new String(mapper.writeValueAsString(alertList.get(loop))));
        }

    }

    /**
     * Method converts single particular object to Json, returns it.
     */
    public String convertAlertDtoToJson(AlertDTO alertDto) throws JsonProcessingException {

        return new String(mapper.writeValueAsString(alertDto));
    }

    /*
     * Method creates new list, populates it with objects from alertList
     * that match both field and value
     */
    public List<AlertDTO> groupByField(String field, String value) throws SQLException, JsonProcessingException {

        List<AlertDTO> groupList = new ArrayList<AlertDTO>();


        // Was having issues with ignoring the quote marks within
        // the json objects, so now using .contains(value) to search
        // For substring instead - works currently.

        if (field.equalsIgnoreCase("id")) {

            for (int loop = 0; loop < alertList.size(); loop++) {
                if (alertList.get(loop).getId().contains(value)) {
                    groupList.add(alertList.get(loop));
                }
            }

        }

        if (field.equalsIgnoreCase("desc")) {

            for (int loop = 0; loop < alertList.size(); loop++) {
                if (alertList.get(loop).getDesc().contains(value)) {
                    groupList.add(alertList.get(loop));
                }
            }

        }



        // if searching for objects that need attention, value must be either
        // true or false.
        if (field.equalsIgnoreCase("hasrfi")) {

            for (int loop = 0; loop < alertList.size(); loop++) {
                if (value.equalsIgnoreCase("true") && alertList.get(loop).isHasRfi() == true) {
                    groupList.add(alertList.get(loop));
                } else if(value.equals("false") && alertList.get(loop).isHasRfi() == false){
                    groupList.add(alertList.get(loop));
                }
            }

        }


        return groupList;
    }

    /**
     * Method manually populates alertList, just for testing purposes
     */
    public void setTestList() throws JsonProcessingException {

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

        alert1.setDesc("Big bad");
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

        alertList.add(alert1);
        alertList.add(alert2);
        alertList.add(alert3);
        alertList.add(alert4);
        alertList.add(alert5);
        alertList.add(alert6);
        alertList.add(alert7);
        alertList.add(alert8);

    }

}