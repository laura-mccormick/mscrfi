package objects;

import converter.FileConverter;

import java.io.File;
import java.io.IOException;

public class AttachmentDTO {

    private String attachmentId;
    private String messageId;
    private byte[] fileAsByteArray;
    private String fileName;
    private File fileAsFile;

    static FileConverter converter = new FileConverter();

    public AttachmentDTO() {
    }

    public AttachmentDTO(String attachmentId, String messageId, byte[] fileAsByteArray, String fileName) throws IOException {
        this.attachmentId = attachmentId;
        this.messageId = messageId;
        this.fileAsByteArray = fileAsByteArray;
        this.fileAsFile = converter.byteArrayToFile(fileAsByteArray, fileName);
        this.fileName = fileName;
    }

    public AttachmentDTO(String attachmentId, String messageId, File fileAsFile, String fileName) throws IOException {
        this.attachmentId = attachmentId;
        this.messageId = messageId;
        this.fileAsByteArray = converter.fileToByteArrayConverter(fileAsFile);
        this.fileAsFile = fileAsFile;
        this.fileName = fileName;
    }



    public File getFileAsFile() {
        return fileAsFile;
    }

    public void setFileAsFile(File fileAsFile) {
        this.fileAsFile = fileAsFile;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public byte[] getFileAsByteArray() {
        return fileAsByteArray;
    }

    public void setFileAsByteArray(byte[] fileAsByteArray) {
        this.fileAsByteArray = fileAsByteArray;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
