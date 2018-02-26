package objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MessageDTO.class)
@TestPropertySource("classpath:test.properties")
public class MessageDTOTest {

    @Autowired
    private MessageDTO message;

    @Test
    public void testMessageDefaultConstructor() {

        MessageDTO messageTest = new MessageDTO();

        assertNotNull(messageTest);

    }

    @Test
    public void testMessageConstructorWithArgs() {

        MessageDTO messageTest = new MessageDTO("Message1", "Conversation1", "Subject1", "Sender1", "Recipient1", "body1", "yesterday");

        assertNotNull(messageTest.getMessageId(), messageTest.getConversationId());
        assertNotNull(messageTest.getSubject(), messageTest.getSender());
        assertNotNull(messageTest.getRecipient(), messageTest.getBody());
        assertNotNull(messageTest.getTimestamp());
        assertNotNull(messageTest);
    }



    @Test
    public void testMessageIDGetterAndSetter() {

        String expected = "Message1";

        message.setMessageId(expected);

        String actual = message.getMessageId();

        assertEquals(actual, expected);
    }

    @Test
    public void testConversationIDGetterAndSetter() {

        String expected = "Conversation1";

        message.setConversationId(expected);

        String actual = message.getConversationId();

        assertEquals(actual, expected);
    }

    @Test
    public void testSubjectGetterAndSetter() {

        String expected = "Subject1";

        message.setSubject(expected);

        String actual = message.getSubject();

        assertEquals(actual, expected);
    }

    @Test
    public void testSenderGetterAndSetter() {

        String expected = "Sender1";

        message.setSender(expected);

        String actual = message.getSender();

        assertEquals(actual, expected);
    }

    @Test
    public void testRecipientGetterAndSetter() {

        String expected = "Recipient1";

        message.setRecipient(expected);

        String actual = message.getRecipient();

        assertEquals(actual, expected);
    }

    @Test
    public void testBodyGetterAndSetter() {

        String expected = "Body1";

        message.setBody(expected);

        String actual = message.getBody();

        assertEquals(actual, expected);
    }

    @Test
    public void testTimeStampGetterAndSetter() {

        String expected = "Yesterday1";

        message.setTimestamp(expected);

        String actual = message.getTimestamp();

        assertEquals(actual, expected);
    }

}
