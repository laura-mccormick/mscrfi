package objects;

public class ConversationDTO {


    public ConversationDTO() {

    }

    public ConversationDTO(String alertId, String conversationId, String subject, String sender, String recipient, boolean open, String dateOpened, String dateClosed) {
        this.alertId = alertId;
        this.conversationId = conversationId;
        this.subject = subject;
        this.sender = sender;
        this.recipient = recipient;
        this.open = open;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
    }

    private String alertId;
    private String conversationId;
    private String subject;
    private String sender;
    private String recipient;
    private boolean open;
    private String dateOpened;
    private String dateClosed;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
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

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
