package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import objects.MessageDAO;
import objects.MessageDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.List;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/message")
public class MessageController extends HttpServlet{

    private MessageDAO dao = new MessageDAO();

    public MessageController() throws SQLException {
    }


    @RequestMapping("/messagesListFullDB")
    @ResponseBody
    public List<MessageDTO> returnMessageListFullDB() throws SQLException {

        return dao.returnMessageListFullDB();

    }


    @RequestMapping("/messagesListFullDummy")
    @ResponseBody
    public List<MessageDTO> returnMessageListFullyDummy() throws SQLException, JsonProcessingException {

        return dao.returnMessageListFullDummy();

    }


    @RequestMapping("/messagesListByRfiIdDB/{rfiId}")
    @ResponseBody
    public List<MessageDTO> returnMessagesListByRfiIdDB(@PathVariable("rfiId") String rfiId) throws SQLException, JsonProcessingException {

        return dao.returnMessageListByIdDB(rfiId);

    }


    @RequestMapping("/messagesListByRfiIdDummy/{rfiId}")
    @ResponseBody
    public List<MessageDTO> returnMessagesListByRfiIdDummy(@PathVariable("rfiId") String rfiId) throws SQLException, JsonProcessingException {

        return dao.returnMessageListByIdDummy(rfiId);

    }



}