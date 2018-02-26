package objects;

public class MessageDTO {


    public MessageDTO() {

    }

    public MessageDTO(String messageId, String conversationId, String subject, String sender, String recipient, String body,
                      String timestamp) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.subject = subject;
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.timestamp = timestamp;
    }


    private String conversationId;
    private String messageId;
    private String subject;
    private String sender;
    private String recipient;
    private String body;
    private String timestamp;


    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
