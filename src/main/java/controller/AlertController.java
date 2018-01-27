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

    static AlertDAO ad = new AlertDAO(false);
    /**
     * Will not work until DB gets sorted
     * Method loads both the object and JSON lists
     */
    @RequestMapping("/listLoad")
    @ResponseBody
    public void listLoader() throws SQLException, JsonProcessingException {

        ad.alertDtoLoad();
        ad.convertAlertListToJson();

    }

    /**
     * Will not work until DB gets sorted
     * Method clears the object list, populates it again then returns it
     */
    @RequestMapping("/fullListActual")
    @ResponseBody
    public List<AlertDTO> refreshDTOList() throws SQLException, JsonProcessingException {
        AlertDAO.alertList.clear();
        ad.alertDtoLoad();
        return AlertDAO.alertList;
    }


    /**
     * Refreshes the list with test data whilst DB is down
     */
    @RequestMapping("/fullListDummy")
    @ResponseBody
    public static List<AlertDTO> listObjectTest() throws JsonProcessingException {
        AlertDAO.alertList.clear();
        ad.setTestList();
        AlertDAO.alertList.add(new AlertDTO("69", "this is seriously working?", false));
        AlertDAO.alertList.add(new AlertDTO("33", "Big fan big man", false));
        return AlertDAO.alertList;
    }


    /**
     * Method searches by the field first, then the value of the field.
     * Can search by any of the attributes of the DTO, except blob atm, functionality required
     * "Attention" is boolean, so searching searchBy/attention/true will return
     * All objects/alerts with an attachment
     */
    @RequestMapping(value = "/searchby/{field}/{value}", method = RequestMethod.GET)
    @ResponseBody
    public List<AlertDTO> searchByReturnTest(@PathVariable("field") String field, @PathVariable("value") String value)
            throws JsonProcessingException, SQLException {

        return ad.groupByField(field, value);
    }
}