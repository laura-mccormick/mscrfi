package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import objects.AttachmentDTO;
import objects.ConversationDAO;
import objects.ConversationDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController {

    private ConversationDAO dao = new ConversationDAO();

    @RequestMapping("/attachmentListFullDummy")
    @ResponseBody
    public List<AttachmentDTO> returnConversationListFullDB() throws SQLException, IOException {

        List<AttachmentDTO> files = new ArrayList<>();
        File file1 = new File("BackendTransfer/testfile.txt");
        File file2 = new File("BackendTransfer/testfile copy.txt");
        File file3 = new File("BackendTransfer/testfile copy 2.txt");
        File file4 = new File("BackendTransfer/testfile copy 3.txt");
        File file5 = new File("BackendTransfer/testfile copy 4.txt");

        files.add(new AttachmentDTO("MSG1ATT1", "MSG1", file1, file1.getName()));
        files.add(new AttachmentDTO("MSG1ATT2", "MSG1", file2, file2.getName()));
        files.add(new AttachmentDTO("MSG1ATT3", "MSG1", file3, file3.getName()));
        files.add(new AttachmentDTO("MSG1ATT4", "MSG1", file4, file4.getName()));
        files.add(new AttachmentDTO("MSG1ATT5", "MSG1", file5, file5.getName()));

        return files;

    }
}
