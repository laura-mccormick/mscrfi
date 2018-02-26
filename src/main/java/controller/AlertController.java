package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import objects.AlertDAO;
import objects.AlertDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/alert")
public class AlertController {

    private static AlertDAO dao = new AlertDAO();

    /**
     * Will not work until DB gets sorted
     * Method clears the object list, populates it again then returns it
     */
    @RequestMapping("/fullListAlertsDB")
    @ResponseBody
    public List<AlertDTO> returnFullAlertsDummy() throws SQLException, JsonProcessingException {

        return dao.returnFullAlertListDB();

    }


    /**
     * Refreshes the list with test data whilst DB is down
     */
    @RequestMapping("/fullListAlertsDummy")
    @ResponseBody
    public static List<AlertDTO> returnFullListAlertsDummy() throws JsonProcessingException {

        return dao.returnFullAlertListDummy();
    }

    /**
     * Refreshes the list with test data whilst DB is down
     */
    @RequestMapping("/getAlertById/{id}")
    @ResponseBody
    public static AlertDTO getAlertById(@PathVariable("id") String id) throws JsonProcessingException, SQLException {

        return dao.returnAlertById(id);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() throws SQLException, JsonProcessingException {

        return "hey dad";

    }

}