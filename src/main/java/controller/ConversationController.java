package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import objects.ConversationDAO;
import objects.ConversationDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/conversation")
public class ConversationController {



    private static ConversationDAO dao = new ConversationDAO();

    private static List<ConversationDTO> list;

    static {
        try {
            list = dao.returnConversationListFullDummy();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/conversationListFullDB")
    @ResponseBody
    public List<ConversationDTO> returnConversationListFullDB() throws SQLException {

        return dao.returnConversationListFullDB();

    }

    @RequestMapping("/conversationListFullDummy")
    @ResponseBody
    public List<ConversationDTO> returnConversationListFullDummy() throws SQLException, JsonProcessingException {

        return list;

    }

    @RequestMapping("/conversationListByAlertIdDB/{alertId}")
    @ResponseBody
    public List<ConversationDTO> returnConversationListByIdDB(@PathVariable("alertId") String alertId) throws SQLException, JsonProcessingException {

        return dao.returnConversationListByAlertIDDB(alertId);

    }

    @RequestMapping("/conversationListByAlertIdDummy/{alertId}")
    @ResponseBody
    public List<ConversationDTO> returnConversationListByIdDummy(@PathVariable("alertId") String alertId) throws SQLException, JsonProcessingException {

        return dao.returnConversationListByIdDummy(alertId);

    }

//    @RequestMapping("/changeConversation/{conversationId}")
//    @ResponseBody
//    public void changeConversation(@PathVariable("conversationId") String conversationId) throws JsonProcessingException, SQLException {
//
//        ConversationDTO conversation = dao.returnConversationObjectByConversationId(conversationId);
//
//        if(conversation.isOpen()) {
//            dao.updateConversationIsOpen(conversation.getConversationId(), false);
//        } else {
//            dao.updateConversationIsOpen(conversation.getConversationId(), true);
//        }
//    }

    @RequestMapping("/changeConversation/{conversationId}")
    @ResponseBody
    public void changeConversation(@PathVariable("conversationId") String conversationId) throws JsonProcessingException, SQLException {

        System.out.println("It got hit!");
        System.out.println(conversationId);

        int thisOne = 10000000;

        for(int loop = 0; loop < list.size(); loop++){

            if(this.list.get(loop).getConversationId().equalsIgnoreCase(conversationId)){

                if(this.list.get(loop).isOpen()){
                    this.list.get(loop).setOpen(false);
                    System.out.println("Conversation was true but now it's " + list.get(loop).isOpen());
                    thisOne = loop;

                } else if(!list.get(loop).isOpen()) {
                    this.list.get(loop).setOpen(true);
                    System.out.println("Conversation was false but now it's " + list.get(loop).isOpen());
                    thisOne = loop;
                }
            }

        }



        System.out.println("Final check it is " + list.get(thisOne).isOpen());
    }

}
