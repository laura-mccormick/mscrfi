package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import objects.RFIDAO;
import objects.RFIDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/rfi")
public class RFIController extends HttpServlet{

    static RFIDAO dao = new RFIDAO();

    @RequestMapping("/fullListActual")
    @ResponseBody
    public List<RFIDTO> returnFullListActual() throws SQLException {


        return dao.returnFullRFIList();

    }


    @RequestMapping("/fullListDummy")
    @ResponseBody
    public List<RFIDTO> returnFullListDummy() throws SQLException, JsonProcessingException {


        return dao.returnFillListDummy();

    }

    @RequestMapping(value = "/test/{boy}/", method= RequestMethod.GET)
    public @ResponseBody
    List<RFIDTO> returnListById(@PathVariable(value="boy") String boy) throws JsonProcessingException {

        List<RFIDTO> list = new ArrayList<>();
        return dao.returnFullListDummyById(boy);
    }


}