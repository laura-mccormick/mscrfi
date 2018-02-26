package objects;

import converter.FileConverter;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDAO {

    private FileConverter converter = new FileConverter();

    public void pushAttachmentToDB(List<File> files, String messageId) throws IOException, SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");
        String sql = "INSERT INTO rfiTool.attachments (attachmentId, messageId, fileAsByteArray, fileName) VALUES(?, ?, ?, ?)";
        PreparedStatement prepSt = connection.prepareStatement(sql);

        int attachNo = 1;

        for(File file : files) {

            String fileName = file.getName();
            byte[] fileByte = converter.fileToByteArrayConverter(file);

            System.out.println(file.getAbsolutePath());
            System.out.println(file.length());

            prepSt.setString(1, messageId + "ATT" + attachNo);
            prepSt.setString(2, messageId);
            prepSt.setBytes(3, fileByte);
            prepSt.setString(4, fileName);

            prepSt.executeUpdate();
            attachNo++;
        }


        prepSt.close();

    }

    public List<AttachmentDTO> returnListAttachmentsByMessageId(String messageId) throws SQLException, IOException {

        List<AttachmentDTO> attachments = new ArrayList<>();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RFITool", "root",
                "Waffle12");

        Statement st = connection.createStatement();

        String sql = String.format("SELECT * FROM rfiTool.attachments WHERE messageId = '%s'", messageId);

        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){

            attachments.add(new AttachmentDTO(rs.getString(1), rs.getString(2), rs.getBytes(3), rs.getString(4)));

        }

        return attachments;
    }

    public List<File> returnListAttachmentsAsFilesByMessageId(String messageId) throws SQLException, IOException {


        List<AttachmentDTO> attachList = returnListAttachmentsByMessageId(messageId);
        List<File> fileList = new ArrayList<>();
        FileConverter fileConverter = new FileConverter();

        for(int loop = 0; loop < attachList.size(); loop++){

            File file = fileConverter.byteArrayToFile(attachList.get(loop).getFileAsByteArray(), attachList.get(loop).getFileName());
            fileList.add(file);

        }

        return fileList;

    }
}
